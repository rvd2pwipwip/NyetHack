import java.util.*

class Sword(_name: String) {
    var name = _name
        get() = "The Legendary $field"
    set(value) {
        field = value.lowercase(Locale.getDefault()).reversed()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    init {
        name = _name
    }
}

fun main() {
    val sword = Sword("Excalibur")
    println(sword.name)
    sword.name = "Durandal"
    println(sword.name)
}