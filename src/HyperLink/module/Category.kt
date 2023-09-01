package HyperLink.module

enum class Category(private val label: String) {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    PLAYER("Player"),
    WORLD("World");

    override fun toString(): String {
        return label
    }
}
