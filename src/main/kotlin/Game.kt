import java.util.*

fun main() {

    Game.play()

}

object Game {
    private var playInProgress = true
    private val player = Player("Madrigal")
    private var currentRoom: Room = TownSquare()

    private var worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    init {
        println("Welcome, adventurer.")
        player.castFireball()
    }

    fun play() {
        while (playInProgress) {
            println(currentRoom.description())
            println(currentRoom.load())
            // Aura
            val auraColor = player.auraColor()

            // Player status
            printPlayerStatus(player)

            print("> Enter your command: ")
            println(GameInput(readLine()).processCommand())
        }
    }

    private fun printPlayerStatus(player: Player) {
        println("Aura: ${player.auraColor()} Blessed: ${if (player.isBlessed) "YES" else "NO"}")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() = when (command.lowercase()) {
            "quit", "exit" -> {
                playInProgress = false
                "Farewell..."
            }
            "map"          -> map()
            "move"         -> move(argument)
            else           -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"
    }

    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.uppercase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw  IllegalStateException("$direction is out of bounds.")
            }

            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            "OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        } catch (e: Exception) {
            "Invalid direction: $directionInput."
        }

    fun map(): String {
        var mapString = ""
        worldMap.forEach {
            it.forEach { room ->
                mapString += if (room == currentRoom) "x " else "o "
            }
            mapString += "\n"
        }
        return mapString.trim()
    }
}