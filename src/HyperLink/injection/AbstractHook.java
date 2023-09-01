package HyperLink.injection;

import org.objectweb.asm.tree.ClassNode;

public abstract class AbstractHook {
    public abstract void hook(ClassNode cn);
}
