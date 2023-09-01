package HyperLink;

import HyperLink.util.FileUtil;
import HyperLink.util.Kernel32;
import HyperLink.util.LogUtil;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loader {

    private static final Kernel32 kernel32 = Native.loadLibrary("kernel32.dll", Kernel32.class, W32APIOptions.ASCII_OPTIONS);

    public static void main(String[] args) throws Throwable {
        OptionParser optionparser = new OptionParser();
        optionparser.allowsUnrecognizedOptions();
        OptionSpec<File> clientPath = optionparser.accepts("path").withRequiredArg().ofType(File.class);
        OptionSet optionset = optionparser.parse(args);

        File clientJar = optionset.has(clientPath) ? optionset.valueOf(clientPath) : getJarFile();
        File loaderDLL = new File("loader.dll");

        if (clientJar == null) {
            LogUtil.log("Failed to inject due to client jar is null");
            return;
        } else {
            if (!clientJar.exists()) {
                LogUtil.log("Failed to inject due to client jar not exists");
            }
        }

        // 释放文件
        LogUtil.log("Extracting loader...");
        FileUtil.write(loaderDLL, FileUtil.read(Loader.class.getResourceAsStream("/tools/loader.dll")));

        // 寻找我的世界
        final List<Integer> pids = new ArrayList<>();

        WinDef.HWND desktopWindow = User32.INSTANCE.GetDesktopWindow();
        WinDef.HWND hwnd = User32.INSTANCE.GetWindow(desktopWindow, new WinDef.DWORD(User32.GW_CHILD));
        IntByReference pointer = new IntByReference();
        char[] charArray = new char[1024];

        LogUtil.log("Minecraft Instances: (Type a number or pid to select)");
        do {
            hwnd = User32.INSTANCE.GetWindow(hwnd, new WinDef.DWORD(User32.GW_HWNDNEXT));
            int length = User32.INSTANCE.GetClassName(hwnd, charArray, charArray.length);
            String className = new String(charArray, 0, length);

            if (!className.equals("LWJGL")) {
                continue;
            }

            length = User32.INSTANCE.GetWindowText(hwnd, charArray, charArray.length);
            String title = new String(charArray, 0, length);

            User32.INSTANCE.GetWindowThreadProcessId(hwnd, pointer);

            pids.add(pointer.getValue());

            System.out.println(repeat(" ", LogUtil.getFormatDate().length()) + String.format("%s, %s, %s", pids.indexOf(pointer.getValue()) + 1, pointer.getValue(), title));

        } while (hwnd != null);

        if (pids.size() == 0) {
            LogUtil.log("No minecraft found.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        int select = 1;
        if (pids.size() > 1) {
            System.out.print(repeat(" ", LogUtil.getFormatDate().length()) + "> ");

            String target = scanner.nextLine();

            try {
                select = Integer.parseInt(target);
            } catch (NumberFormatException e) {
                LogUtil.log("Please input a correct number!");
                return;
            }

            if (select < 1) {
                LogUtil.log("Please input a correct number!");
                return;
            }
        }

        // 准备jar文件，准备注入
        String fileName = "hyperlink.jar";
        String userDir = System.getProperty("user.home");
        String filePath = userDir + File.separator + fileName;

        // 拷贝jar
        File file = new File(filePath);
        LogUtil.log("Jar path: " + clientJar.getAbsolutePath());
        LogUtil.log("Client path: " + file.getAbsolutePath());
        Files.copy(clientJar.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // 获取pid，执行注入
        int pid;

        if (select > pids.size()) {
            pid = select;
        } else {
            pid = pids.get(select - 1);
        }

        LogUtil.log("Inject pid: " + pid);

        injectDLL(pid, loaderDLL.getAbsolutePath());
    }

    public static File getJarFile() {
        try {
            File jarFile = new File(Loader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            if (jarFile.isFile()) {
                return jarFile;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String repeat(String source, int repeat) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < repeat) {
            sb.append(source);
            i++;
        }
        return sb.toString();
    }

    // https://github.com/LoRyu/DLLInjector/blob/main/src/cn/loryu/dllinjector/Frame.java
    public static boolean injectDLL(int processID, String dllName) {
        BaseTSD.DWORD_PTR processAccess = new BaseTSD.DWORD_PTR(0x43A);

        WinNT.HANDLE hProcess = kernel32.OpenProcess(processAccess, new WinDef.BOOL(false), new BaseTSD.DWORD_PTR(processID));
        if (hProcess == null) {
            System.out.println("Handle was NULL! Error: " + kernel32.GetLastError());
            return false;
        }

        BaseTSD.DWORD_PTR loadLibraryAddress = kernel32.GetProcAddress(kernel32.GetModuleHandle("KERNEL32"), "LoadLibraryA");
        if (loadLibraryAddress.intValue() == 0) {
            System.out.println("Could not find LoadLibrary! Error: " + kernel32.GetLastError());
            return false;
        }

        WinDef.LPVOID dllNameAddress = kernel32.VirtualAllocEx(hProcess, null, (dllName.length() + 1), new BaseTSD.DWORD_PTR(0x3000), new BaseTSD.DWORD_PTR(0x4));
        if (dllNameAddress == null) {
            System.out.println("dllNameAddress was NULL! Error: " + kernel32.GetLastError());
            return false;
        }

        Pointer m = new Memory(dllName.length() + 1);
        m.setString(0, dllName);

        boolean wpmSuccess = kernel32.WriteProcessMemory(hProcess, dllNameAddress, m, dllName.length(), null).booleanValue();
        if (!wpmSuccess) {
            System.out.println("WriteProcessMemory failed! Error: " + kernel32.GetLastError());
            return false;
        }

        BaseTSD.DWORD_PTR threadHandle = kernel32.CreateRemoteThread(hProcess, 0, 0, loadLibraryAddress, dllNameAddress, 0, 0);
        if (threadHandle.intValue() == 0) {
            System.out.println("threadHandle was invalid! Error: " + kernel32.GetLastError());
            return false;
        }

        kernel32.CloseHandle(hProcess);

        return true;
    }


}
