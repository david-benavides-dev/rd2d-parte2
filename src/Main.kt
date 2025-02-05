import classes.Robot
import java.util.*

fun main() {

    // * Array con instancia de robots, uno por cada tipo (0 a 3).
    val ejercitoRobots: Array<Robot> = arrayOf(
        Robot("RD2D", 0),
        Robot("DAW1A", 1),
        Robot("DAW1B", 2),
        Robot("DAM1", 3)
    )

    val movimientosUsuario = pedirMovimientos()

//    val movimientosArray: Array<Array<Int>> = arrayOf(
//        arrayOf(1, -5, 0, -9),
//        arrayOf(3, 3, 5, 6, 1, 0, 0, -7),
//        arrayOf(2, 1, 0, -1, 1, 1, -4),
//        arrayOf(),
//        arrayOf(3, 5)
//    )

    // * Iteración sobre un conjunto de movimientos que actualiza
    // * y muestra la posición de cada robot.
    for (robot in ejercitoRobots) {
        robot.mover(movimientosUsuario)
        println(robot)
    }
}

/**
 * Solicita al usuario que ingrese movimientos numéricos y los almacena en una lista.
 * Los números deben estar entre -20 y 20.
 *
 * @return Una array de enteros con los movimientos ingresados por el usuario.
 */
fun pedirMovimientos(): Array<Int> {

    var terminarObtecionNumeros = false

    val movimientos = mutableListOf<Int>()

    do {
        println("¿Quieres introducir movimientos? (X para salir) >> ")

        val quieroMovimiento = readln().uppercase(Locale.getDefault())

        if (quieroMovimiento == "X") {
            terminarObtecionNumeros = true
        } else {
            try {
                println("Introduce movimiento >> ")
                val pedirNumero = readln().toInt()
                if (pedirNumero >= -20 && pedirNumero <= 20) {
                    movimientos.add(pedirNumero)
                } else {
                    println("*ERROR* El movimiento debe estar entre -20 y 20.")
                }
            } catch (e: NumberFormatException) {
                println("*ERROR* No has introducido un número.")
            }
        }
    } while (!terminarObtecionNumeros)
    return movimientos.toTypedArray()
}