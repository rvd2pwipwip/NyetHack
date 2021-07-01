import java.io.File
import java.util.*
import kotlin.math.roundToInt

const val TAVERN_NAME = "Taernyl's Folly"

var playerGold = 10
var playerSilver = 10
val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()
val menuList = File("data/tavern-menu-items.txt").readText().split("\n")

fun main() {
    if (patronList.contains("Eli")) {
        println("The tavern master says: Eli's in the back playing cards. ")
    } else {
        println("The tavern master says: Eli isn't here.")
    }

    if (patronList.containsAll(listOf("Sophie", "Mordoc"))) {
        println("The tavern master says: Yea, they're seated by the stew kettle.")
    } else {
        println("The tavern master says: Nay, they departed hours ago.")
    }

    (0..9).forEach {
        val first = patronList.shuffled().first()
        val last = lastName.shuffled().first()
        val name = "$first $last"
        uniquePatrons += name
    }
    println(uniquePatrons)

//    var orderCount = 0
//    while (orderCount <= 9) {
//        placeOrder(uniquePatrons.shuffled().first(), menuList.shuffled().first())
//        orderCount++
//    }
    printMenu(menuList)
}

fun performPurchase(price: Double) {
    displayBalance()
    val totalPurse = playerGold + playerSilver / 100.0
    println("Total purse: $totalPurse")
    println("Purchasing item for $price")

    val remainingBalance = totalPurse - price
    println("Remaining balance: ${"%.2f".format(remainingBalance)}")

    val remainingGold = remainingBalance.toInt()
    val remainingSilver = (remainingBalance % 1 * 100).roundToInt()
    playerGold = remainingGold
    playerSilver = remainingSilver
    displayBalance()
}

fun displayBalance() {
    println("Player's purse balance: Gold: $playerGold , Silver: $playerSilver")
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

fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order.")

    val (type, name, price) = menuData.split(',')
    val message = "$patronName buys a $name ($type) for $price."
    println(message)

//    performPurchase(price.toDouble())

    val phrase = if (name == "Dragon's Breath") {
        "$patronName exclaims: ${toDragonSpeak("Ah, delicious $name!")}"
    } else {
        "$patronName says: Thanks for the $name."
    }
    println(phrase)
}

fun printMenu(menu: List<String>) {
//    var itemNames = listOf<String>()
//    menu.forEach { item ->
//        itemNames += item.split(',')[1]
//    }
//    val longest = itemNames.maxByOrNull { it.length }

    val menuTitle = "*** Welcome to $TAVERN_NAME ***"
    val colWidth = menuTitle.length + 1

    var categories = setOf<String>()
    menu.forEach {
        categories += it.split(',')[0]
    }

    println(menuTitle)
    categories.forEach { category ->
        println(" ".repeat(((colWidth - category.length) - 4) / 2) + "-[$category]-")
        menu.forEach {
            if (it.split(',')[0] == category) {
                val (_, name, price) = it.split(',')
                val nameWords = name.split(' ').toList()
                var capitalized = ""
                nameWords.forEach { word ->
                    capitalized += word.replaceFirstChar { if (it.isLowerCase() && word != "of") it.titlecase() else it.toString() } + " "
                }
                println(capitalized.trim() + ".".repeat(colWidth - capitalized.length - price.length) + price)
            }
        }
    }
}
