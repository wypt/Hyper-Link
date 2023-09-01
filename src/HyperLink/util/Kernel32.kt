package HyperLink.util

import com.sun.jna.Pointer
import com.sun.jna.platform.win32.BaseTSD
import com.sun.jna.platform.win32.WinDef.BOOL
import com.sun.jna.platform.win32.WinDef.LPVOID
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.win32.StdCallLibrary

interface Kernel32 : StdCallLibrary {
    fun OpenProcess(dwDesiredAccess: BaseTSD.DWORD_PTR?, bInheritHandle: BOOL?, dwProcessId: BaseTSD.DWORD_PTR?): WinNT.HANDLE?
    fun GetProcAddress(hModule: WinNT.HANDLE?, lpProcName: String?): BaseTSD.DWORD_PTR?
    fun VirtualAllocEx(hProcess: WinNT.HANDLE?, lpAddress: LPVOID?, dwSize: Int, flAllocationType: BaseTSD.DWORD_PTR?, flProtect: BaseTSD.DWORD_PTR?): LPVOID?
    fun WriteProcessMemory(hProcess: WinNT.HANDLE?, lpBaseAddress: LPVOID?, lpBuffer: Pointer?, nSize: Int, lpNumberOfBytesWritten: Pointer?): BOOL?
    fun CreateRemoteThread(hProcess: WinNT.HANDLE?, lpThreadAttributes: Int, dwStackSize: Int, loadLibraryAddress: BaseTSD.DWORD_PTR?, lpParameter: LPVOID?, dwCreationFlags: Int, lpThreadId: Int): BaseTSD.DWORD_PTR?
    fun CloseHandle(hObject: WinNT.HANDLE?): BOOL?
    fun GetLastError(): Int
    fun GetModuleHandle(string: String?): WinNT.HANDLE?
}
