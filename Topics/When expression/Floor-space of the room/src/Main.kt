
import java.util.Scanner
import kotlin.math.round

fun main() {
    val reader = Scanner(System.`in`)
    val input = readln()
    when (input) {
        "triangle" -> {
            val a = reader.nextInt()
            val b = reader.nextInt()
            val c = reader.nextInt()
            val area = kotlin.math.sqrt(
                ((a + b + c) / 2 * ((a + b + c) / 2 - a) * ((a + b + c) / 2 - b) * ((a + b + c) / 2 - c))
                    .toDouble()
            )
            println((round(area * 10)/10).toDouble())
        }
        "rectangle" -> {
            val a = reader.nextInt()
            val b = reader.nextInt()
            val area = round((a * b).toDouble())
            println(round(area*10)/10)
        }

        "circle" -> {
            val r = reader.nextInt()
            val area = (r * r) * kotlin.math.PI.toDouble()
            println((round(area * 10) / 10).toDouble())
        }
    }
}
