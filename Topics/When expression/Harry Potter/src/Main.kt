fun main() {
    // write your code here
    val input = readln()

    when {
        input == "gryffindor" -> println("bravery")
        input == "hufflepuff" -> println("loyalty")
        input == "slytherin" -> println("cunning")
        input == "ravenclaw" -> println("intellect")
        else -> println("not a valid house")
    }
}
