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
        establecerPosicionesTipo()
    }

    private fun establecerPosicionesTipo() {
        when (serialrobot) {
            // RD2D
            0 -> {
                println("[R2D2] SISTEMA OPERATIVO CARGADO. ESTADO: ÓPTIMO. *BEEP BOOP*")
            }
            // DAW1A
            1 -> {
                posX = Random.nextInt(-5,5)
                posY = 0
                println("[DAW1A] INICIALIZANDO... COORDENADAS ESTABLECIDAS. ESTADO: FUNCIONAL... MÁS O MENOS.")
            }
            // DAW1B
            2 -> {
                posX = 0
                posY = Random.nextInt(-10,10)
                direccion = Direcciones.DIRECCIONES.random()
                println("[DAW1B] *** MÓDULO DE MOVIMIENTO ACTIVADO *** PROCESANDO... *BEEP BOOP*")
            }
            // DAM1
            3 -> {
                posX = Random.nextInt(-5,5)
                posY = Random.nextInt(-5,5)
                direccion = Direcciones.DIRECCIONES.random()
                println("[DAM1] *** ERROR PARCIAL *** INTENTANDO ARRANQUE... SISTEMA EN PROCESO DE RECUPERACIÓN...")
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
     * El número debe estar entre 0 y 3, determinando el tipo y configuración inicial del robot.
     *
     * **Opciones de número de serie:**
     * - `0` → **R2D2**: Comienza en `(0, 0)` con dirección `PositiveY`.
     * - `1` → **DAW1A**: Posición inicial aleatoria en `x` (`-5` a `5`), `y = 0`, dirección `PositiveX`.
     * - `2` → **DAW1B**: Inicia en `x = 0`, `y` aleatoria entre `-10` y `10`, dirección inicial aleatoria.
     * - `3` → **DAM1**: Posición inicial aleatoria en ambos ejes (`x` e `y` entre `-5` y `5`), dirección aleatoria.
     *
     * @param pedirNumeroSerie Mensaje mostrado al usuario para ingresar el número de serie del robot.
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