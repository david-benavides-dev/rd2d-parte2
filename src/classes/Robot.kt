package classes

/**
 * Clase que representa un robot con un sistema de coordenadas algo especialito
 *
 * @property nombre El nombre del robot. Si está en blanco, se solicitará al usuario introducir uno.
 * @property posX La posición en el eje X del robot. Por defecto 0.
 * @property posY La posición en el eje Y del robot. Por defecto 0.
 * @property direccion La dirección en la que se mueve el robot. Por defecto es Direcciones.PositiveY.
 */
class Robot(private var nombre: String = "", private var posX: Int = 0, private var posY: Int = 0, private var direccion: Direcciones = Direcciones.PositiveY) {

    init {
        if (nombre.isBlank()) { obtenerNombre() }
    }

    /**
     * Solicita al usuario que introduzca el nombre del robot.
     *
     * @param pedirName El mensaje que se muestra al usuario para introducir el nombre.
     */
    private fun obtenerNombre(pedirName: String = "Introduce el nombre del robot >> ") {
        while (this.nombre.isBlank()) {
            print(pedirName)
            this.nombre = readln()
            if (this.nombre == "") {
                println("*ERROR* El nombre del robotrón no puede estar vacío.")
            }
        }
    }

    /**
     * Mueve el robot en función de un array de movimientos.
     *
     * Cada movimiento se aplica en la dirección actual del robot, y luego se cambia la dirección
     * en contra de las agujas del reloj.
     *
     * @param movimientos Un array de enteros que representa los movimientos a realizar.
     */
    fun mover(movimientos: Array<Int>) {
        for (movimiento in movimientos) {
            when(direccion) {
                Direcciones.PositiveY -> {
                    posY += movimiento
                    direccion = Direcciones.NegativeX
                }
                Direcciones.NegativeX -> {
                    posX -= movimiento
                    direccion = Direcciones.NegativeY
                }
                Direcciones.NegativeY -> {
                    posY -= movimiento
                    direccion = Direcciones.PositiveX
                }
                Direcciones.PositiveX -> {
                    posX += movimiento
                    direccion = Direcciones.PositiveY
                }
            }
        }
    }

    /**
     * Obtiene la dirección actual del robot.
     *
     * @return La dirección actual del robot.
     */
    private fun obtenerDireccion(): Direcciones {
        return this.direccion
    }

    /**
     * Obtiene la posición actual del robot como un par de coordenadas (posX, posY).
     *
     * @return Un par que contiene la posición X e Y del robot.
     */
    private fun obtenerPosicion(): Pair<Int,Int> {
        return Pair(posX, posY)
    }

    /**
     * Devuelve una representación en forma de cadena del estado actual del robot.
     *
     * @return Una cadena que describe la posición y dirección del robot.
     */
    override fun toString(): String {
        return "$nombre está en ${obtenerPosicion()} ${obtenerDireccion()}"
    }
}