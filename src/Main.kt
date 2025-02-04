import classes.Robot

fun main() {
    // Creación de instancia instancia de un objeto utilizando la clase Robot.
    val robot1 = Robot()

    // * Estructura de movimientos en un array de arrays de enteros.
    val movimientosArray: Array<Array<Int>> = arrayOf(
        arrayOf(1, -5, 0, -9),
        arrayOf(3, 3, 5, 6, 1, 0, 0, -7),
        arrayOf(2, 1, 0, -1, 1, 1, -4),
        arrayOf(),
        arrayOf(3, 5)
    )

    // * Iteración sobre cada conjunto de movimientos que actualiza
    // * y muestra la posición del robot.
    for (movimientos in movimientosArray) {
        robot1.mover(movimientos)
        println(robot1)
    }
}