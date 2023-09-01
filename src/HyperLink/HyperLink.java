package HyperLink;

import HyperLink.config.Config;
import HyperLink.events.Render2DEvent;
import HyperLink.font.FontManager;
import HyperLink.injection.TransformManager;
import HyperLink.mapping.SrgParser;
import HyperLink.module.ModuleManager;
import HyperLink.plugin.PluginManager;
import HyperLink.renderer.ScreenManager;
import HyperLink.wrapper.InstanceManager;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.opengl.Display;

public class HyperLink {

    public static final String property_version = "1.0";
    public static final String[] property_non_rename_clients = new String[]{"Lunar"};
    public static final String[] property_1_8_9_clients = new String[]{"1.8.9"};

    private static HyperLink instance;
    private final InstanceManager instanceManager;
    private final ModuleManager moduleManager;
    private final TransformManager transformManager;
    private final FontManager fontManager;
    private final ScreenManager screenManager;

    private final PluginManager pluginManager;
    private final Config config;
    private SrgParser mapping;

    public HyperLink() throws Throwable {
        instance = this;
        setupMapping();
        Class<?> mc = Class.forName("net.minecraft.client.Minecraft");
        Object instance = mc.getDeclaredMethod(mapping.getMethodName("net/minecraft/client/Minecraft/getMinecraft")).invoke(null);
        fontManager = new FontManager();
        instanceManager = new InstanceManager(instance);
        transformManager = new TransformManager();
        screenManager = new ScreenManager();
        moduleManager = new ModuleManager();
        pluginManager = new PluginManager();
        config = new Config("modules");

        transformManager.hook();
        EventManager.register(this);
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        fontManager.loadFonts();
        moduleManager.addModules();
        pluginManager.loadPlugins();
        instanceManager.getMinecraft().updateFramebufferSize();
        config.load();
        final Thread thread = new Thread() {
            @Override
            public void run() {
                while (instanceManager.getMinecraft().running()) {
                    try {
                        config.save();
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                interrupt();
            }
        };
        thread.setDaemon(true);
        thread.start();
        EventManager.unregister(this);
    }

    public static native int setClassByte(Class<?> clazz, byte[] bytes);

    public static native byte[] getClassByte(Class<?> clazz);

    public static native Class<?> loadClassByte(byte[] bytes);

    private void setupMapping() {
        try {
            Class<?> mc = Class.forName("net.minecraft.client.Minecraft");
            mc.getDeclaredMethod("getMinecraft");
            mapping = new SrgParser(""); // MCP
        } catch (Exception e) {
            for (String title : property_non_rename_clients) {
                if (Display.getTitle().contains(title)) {
                    mapping = new SrgParser("");
                    return;
                }
            }
            for (String title : property_1_8_9_clients) {
                if (Display.getTitle().contains(title)) {
                    mapping = new SrgParser("1.8.9.srg");
                    return;
                }
            }
        }
    }

    public static HyperLink getInstance() {
        return instance;
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public SrgParser getMapping() {
        return mapping;
    }

    public InstanceManager getInstanceManager() {
        return instanceManager;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
