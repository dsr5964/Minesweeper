fun main() {
    val input = readln().toInt()
    when {
        input == 2 -> println("Yes!")
        input in 1..4 && input != 2 -> println("No!")
        
        else -> println("Unknown number")
    }
}
