import kotlin.math.pow

fun main() {
    val name = "Madrigal"
    var healthPoints = 89
    var isBlessed = true
    val isImmortal = false

    // Karma
    val karmaStatus = when ((Math.random().pow((110 - healthPoints) / 100.0) * 20 ).toInt()) {
        in 0..5   -> "RED"
        in 6..10  -> "ORANGE"
        in 11..15 -> "PURPLE"
        in 16..20 -> "GREEN"
        else      -> "NONE"
    }

    // Aura
    val auraVisible = isBlessed && healthPoints > 50 || isImmortal
    val auraColor = karmaStatus

    val healthStatus = when (healthPoints) {
        100       -> "is in excellent condition!"
        in 90..99 -> "has a few scratches."
        in 75..89 -> if (isBlessed) {
            "has some minor wounds but is healing quite quickly!"
        } else {
            "has some minor wounds."
        }
        in 15..74 -> "looks pretty hurt."
        else      -> "is in awful condition!"
    }

    val statusFormatString = "(HP)(A)(B) -> H"
    val statusString = statusFormatString
        .replace("HP", "HP: $healthPoints")
        .replace("A", "Aura: $auraColor")
        .replace("B","Blessed: ${if (isBlessed) "YES" else "NO"}")
        .replace("-> H", "-> $healthStatus")

    println(statusString)

    // Player status
//    println("(Aura: $auraColor)  (Blessed: ${if (isBlessed) "YES" else "NO"})")
//    println("$name $healthStatus")
}