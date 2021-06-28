

const val TAVERN_NAME = "Taernyl's Folly"
const val PINT = 0.125
const val DRAGON_COIN_RATE = 1.43

var playerDragonCoin = 5.0
var cask = 5.0

fun main() {
    placeOrder("shandy,Dragon's Breath,5.91")
    placeOrder("shandy,Dragon's Breath,5.91")
}

fun performPurchase(price: Double) {
    displayBalance()

    val totalPurse = (playerDragonCoin * DRAGON_COIN_RATE)

    println("Total purse: $totalPurse")
    println("Purchasing item for $price")

    val remainingBalance = (totalPurse - price) / 1.43
    println("Remaining balance: ${"%.4f".format(remainingBalance)}")

    playerDragonCoin = remainingBalance
    displayBalance()

    var order = 12 * PINT

    getCaskBalance(order)
    displayCask()
}

fun displayCask() {
    println("Tavern's cask balance: $cask gallons")
}

fun displayBalance() {
    println("Player's purse balance: Dragoncoin: ${"%.4f".format(playerDragonCoin)}")
}

private fun toDragonSpeak(phrase: String) = phrase.replace(Regex("[aeiou]")) {
    when (it.value) {
        "a" -> "4"
        "e" -> "3"
        "i" -> "1"
        "o" -> "0"
        "u" -> "|_|"
        else -> it.value
    }
}

fun placeOrder(menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    val totalPurse = playerDragonCoin * DRAGON_COIN_RATE
    println("Madrigal speaks with $tavernMaster about their order.")


    val (type, name, price) = menuData.split(',')

    if (price.toDouble() > totalPurse) {
        println("$tavernMaster says: Sorry but you are too short on gold for that drink.")
    } else {
        val message = "Madrigal buys a $name ($type) for $price."
        println(message)

        performPurchase(price.toDouble())

        val phrase = if (name == "Dragon's Breath") {
            "Madrigal exclaims: ${toDragonSpeak("Ah, delicious $name!")}"
        } else {
            "Madrigal says: Thanks for the $name."
        }
        println(phrase)
    }

}

fun getCaskBalance(order: Double) {
    cask -= order
}