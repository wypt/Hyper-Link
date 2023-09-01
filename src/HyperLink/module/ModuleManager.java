package HyperLink.module;

import HyperLink.events.KeyEvent;
import HyperLink.module.modules.combat.KillAura;
import HyperLink.module.modules.combat.Velocity;
import HyperLink.module.modules.movement.Speed;
import HyperLink.module.modules.movement.Sprint;
import HyperLink.module.modules.movement.Strafe;
import HyperLink.module.modules.render.FullBright;
import HyperLink.module.modules.render.HUD;
import HyperLink.module.modules.render.Menu;
import HyperLink.module.modules.world.FastPlace;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    @NotNull
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        EventManager.register(this);
    }

    public void addModules() {
        addModule(new KillAura());
        addModule(new Velocity());

        addModule(new HUD());
        addModule(new Menu());
        addModule(new FullBright());

        addModule(new Sprint());
        addModule(new Speed());
        addModule(new Strafe());

        addModule(new FastPlace());
    }

    @NotNull
    public List<Module> getModules() {
        return modules;
    }

    private void addModule(@NotNull Module module) {
        modules.add(module);
    }

    @NotNull
    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    }

    public Module getModule(@NotNull String name, boolean caseSensitive) {
        return modules.stream().filter(mod -> !caseSensitive && name.equalsIgnoreCase(mod.getName()) || name.equals(mod.getName())).findFirst().orElse(null);
    }

    @EventTarget
    public void onKey(KeyEvent event) {
        for (Module module : modules) {
            if (module.getKey() == event.key)
                module.toggle();
        }
    }

}
