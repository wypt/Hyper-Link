package HyperLink.module;

import HyperLink.HyperLink;
import HyperLink.module.properties.Property;
import HyperLink.wrapper.wrappers.MinecraftWrapper;
import HyperLink.wrapper.wrappers.ScaledResolutionWrapper;
import com.darkmagician6.eventapi.EventManager;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module {
    protected static final MinecraftWrapper mc = HyperLink.getInstance().getInstanceManager().getMinecraft();
    private final String name;
    private final String description;
    private final Category category;
    private boolean canBeEnabled;
    private boolean hidden;
    private final List<Property<?>> properties;
    private boolean enabled;
    private float x, y;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<Property<?>> getProperties() {
        return properties;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    private int key;

    public Module(String name, Category category) {
        this(name, "No Description!", category, true, false, Keyboard.KEY_NONE);
    }

    public Module(String name, String description, Category category) {
        this(name, description, category, true, false, Keyboard.KEY_NONE);
    }

    public Module(String name, String description, Category category, boolean canBeEnabled, boolean hidden, int key) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.canBeEnabled = canBeEnabled;
        this.hidden = hidden;
        this.key = key;
        this.properties = new ArrayList<>();
        this.x = new ScaledResolutionWrapper(mc).getScaledWidth();
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.enabled = true;
            EventManager.register(this);
            onEnable();
        } else {
            this.enabled = false;
            EventManager.unregister(this);
            onDisable();
        }
    }

    public void addProperties(Property<?>... properties) {
        this.properties.addAll(Arrays.asList(properties));
    }


    protected void onEnable() {

    }

    protected void onDisable() {

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void toggle() {
        setEnabled(!enabled);
    }
}
