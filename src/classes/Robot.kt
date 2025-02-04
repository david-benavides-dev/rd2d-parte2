package classes
import kotlin.random.Random

/**
 * Clase que representa un robot con un sistema de coordenadas algo especialito
 *
 * @property nombre El nombre del robot. Si está en blanco, se solicitará al usuario introducir uno.
 * @property posX La posición en el eje X del robot. Por defecto 0.
 * @property posY La posición en el eje Y del robot. Por defecto 0.
 * @property direccion La dirección en la que se mueve el robot. Por defecto es Direcciones.PositiveY.
 */
class Robot(private var nombre: String = "", private var serialrobot: Int) {

    private var posX: Int = 0
    private var posY: Int = 0
    private var direccion: Direcciones = Direcciones.PositiveY

    init {
        if (nombre.isEmpty()) { obtenerNombre() }
        if (serialrobot !in 0..3) { obtenerNumeroSerie() }
        cambiarPosicionesSerial()
    }

    private fun cambiarPosicionesSerial() {
        when (serialrobot) {
            // RD2D
            0 -> {
                println("ROBOT TIPO R2D2 LISTO PARA LA ACCIÓN *BEP BEP*")
            }
            // DAW1A
            1 -> {
                println("ROBOT TIPO DAW1A LISTO PARA LA ACCIÓN... MAS O MENOS")
                posX = Random.nextInt(-5,5)
                posY = 0
            }
            // DAW1B
            2 -> {
                println("ROBOT DAW1B TERRRRRRRENEITORRRR READY")
                posX = 0
                posY = Random.nextInt(-10,10)
                direccion = Direcciones.DIRECCIONES.random()
            }
            // DAM1
            3 -> {
                posX = Random.nextInt(-5,5)
                posY = Random.nextInt(-5,5)
                direccion = Direcciones.DIRECCIONES.random()
                println("robot dam1 intentando iniciar")
            }
        }
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
     * Solicita al usuario que introduzca el número de serie del robot.
     * El número debe estar entre 0 y 3.
     * 0 -> R2D2 comienza en (0, 0) y PositiveY
     * 1 -> DAW1A Comienza con posición aleatoria entre -5 y 5 para x y en el eje y en 0. Dirección PositiveX.
     * 2 -> DAW1B comienza en la posición x = 0, pero la posición y es aleatoria entre -10 y 10. Dirección inicial aleatoria.
     * 3 -> DAM1 comienza en una posición aleatoria entre -5 y 5 en cada eje. Dirección inicial aleatoria.
     *
     * @param pedirNumeroSerie El mensaje que se muestra al usuario para introducir el número de serie.
     */
    private fun obtenerNumeroSerie(pedirNumeroSerie: String = "Introduce el número de serie de $nombre >> ") {
        while (this.serialrobot !in 0..3) {
            print(pedirNumeroSerie)
            try {
                this.serialrobot = readln().toInt()
                if(serialrobot !in 0..3) {
                    println("*ERROR* Ese serial no existe.")
                }
            } catch (e: NumberFormatException) {
                println("*ERROR* No has introducido un número.")
            }
        }
    }

    /**
     * Mueve el robot en función de un array de movimientos y el tipo de robot que sea
     *
     * Cada movimiento se aplica en la dirección actual del robot, y luego se cambia la dirección
     * en contra de las agujas del reloj.
     *
     * @param movimientos Un array de enteros que representa los movimientos a realizar.
     */
    fun mover(movimientos: Array<Int>) {

        //         PositiveY
        //             ^
        //             |
        // NegativeX <-+-> PositiveX
        //             |
        //             v
        //         NegativeY

        for (movimiento in movimientos) {
            when(serialrobot) {
                0, 2 -> {
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
                1 -> {
                    when(direccion) {
                        Direcciones.PositiveY -> {
                            posY += movimiento
                            direccion = if (posX > 0) Direcciones.NegativeY else Direcciones.NegativeX
                        }
                        Direcciones.NegativeX -> {
                            posX -= movimiento
                            direccion = if (posX > 0) Direcciones.PositiveX else Direcciones.NegativeX
                        }
                        Direcciones.NegativeY -> {
                            posY -= movimiento
                            direccion = if (posX > 0) Direcciones.NegativeX else Direcciones.PositiveX
                        }
                        Direcciones.PositiveX -> {
                            posX += movimiento
                            direccion = if (posX > 0) Direcciones.PositiveX else Direcciones.NegativeX
                        }
                    }
                }
                3 -> {
                    when(direccion) {
                        Direcciones.PositiveY -> {
                            posY += movimiento
                            direccion = Direcciones.DIRECCIONES.filter { it != direccion }.random()
                        }
                        Direcciones.NegativeX -> {
                            posX -= movimiento
                            direccion = Direcciones.DIRECCIONES.filter { it != direccion }.random()
                        }
                        Direcciones.NegativeY -> {
                            posY -= movimiento
                            direccion = Direcciones.DIRECCIONES.filter { it != direccion }.random()
                        }
                        Direcciones.PositiveX -> {
                            posX += movimiento
                            direccion = Direcciones.DIRECCIONES.filter { it != direccion }.random()
                        }
                    }
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