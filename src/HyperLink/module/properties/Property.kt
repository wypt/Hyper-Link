package HyperLink.module.properties

open class Property<T>(@JvmField val name: String?, @JvmField var value: T) {

    val isVisible: Boolean
        get() = true
}
