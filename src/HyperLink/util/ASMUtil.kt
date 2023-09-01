package HyperLink.util

import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

object ASMUtil {
    fun getMethod(cn: ClassNode, name: String, desc: String): MethodNode {
        return cn.methods
                .stream()
                .filter { mn: MethodNode -> mn.name == name && mn.desc == desc }
                .findFirst()
                .orElse(null)
    }
}
