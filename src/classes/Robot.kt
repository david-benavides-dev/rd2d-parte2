package classes
import kotlin.random.Random

/**
 * Representa un robot con un sistema de coordenadas y diferentes tipos de comportamiento según su modelo.
 *
 * El robot puede ser de distintos modelos, cada uno con una configuración de inicio y un patrón de movimiento específico.
 *
 * @property nombre Nombre del robot. Si no se proporciona, se solicita al usuario.
 * @property modeloRobot Número de modelo del robot. Si es inválido, se solicita al usuario.
 * @property posX Coordenada X del robot.
 * @property posY Coordenada Y del robot.
 * @property direccion Dirección actual en la que se mueve el robot.
 */
class Robot(private var nombre: String = "", private var modeloRobot: Int = -1) {

    private var posX: Int = 0
    private var posY: Int = 0
    private var direccion: Direcciones = Direcciones.PositiveY

    init {
        if (nombre.isEmpty()) { obtenerNombre() }
        if (modeloRobot !in 0..3) { obtenerModeloRobot() }
        establecerPosicionesTipo()
    }

    /**
     * Asigna las posiciones iniciales y dirección del robot según su modelo.
     *
     * Los diferentes modelos tienen condiciones iniciales específicas:
     * - `0` (R2D2): Inicia en `(0, 0)`, dirección `PositiveY`.
     * - `1` (DAW1A): Posición aleatoria en `x` dentro de `[-5, 5]`, `y = 0`.
     * - `2` (DAW1B): `x = 0`, `y` aleatoria en `[-10, 10]`, dirección aleatoria.
     * - `3` (DAM1): Posición aleatoria en ambos ejes `[-5, 5]`, dirección aleatoria.
     */
    private fun establecerPosicionesTipo() {
        when (modeloRobot) {
            // RD2D
            0 -> {
                println("[R2D2] SISTEMA OPERATIVO CARGADO. ESTADO: ÓPTIMO. *BEEP BOOP*")
                println("Estado inicial del robot: ($posX, $posY) - Dirección: $direccion")
            }
            // DAW1A
            1 -> {
                posX = Random.nextInt(-5,5)
                posY = 0
                println("[DAW1A] INICIALIZANDO... COORDENADAS ESTABLECIDAS. ESTADO: FUNCIONAL... MÁS O MENOS.")
                println("Estado inicial del robot: ($posX, $posY) - Dirección: $direccion")
            }
            // DAW1B
            2 -> {
                posX = 0
                posY = Random.nextInt(-10,10)
                direccion = Direcciones.obtenerDireccionRandom()
                println("[DAW1B] *** MÓDULO DE MOVIMIENTO ACTIVADO *** PROCESANDO... *BEEP BOOP*")
                println("Estado inicial del robot: ($posX, $posY) - Dirección: $direccion")
            }
            // DAM1
            3 -> {
                posX = Random.nextInt(-5,5)
                posY = Random.nextInt(-5,5)
                direccion = Direcciones.obtenerDireccionRandom()
                println("[DAM1] *** ERROR PARCIAL *** INTENTANDO ARRANQUE... SISTEMA EN PROCESO DE RECUPERACIÓN...")
                println("Estado inicial del robot: ($posX, $posY) - Dirección: $direccion")
            }
        }
    }

    /**
     * Solicita al usuario que introduzca el nombre del robot.
     *
     * @param pedirName El mensaje que se muestra al usuario a la hora de introducir el nombre.
     */
    private fun obtenerNombre(pedirName: String = "Introduce el nombre del robot >> ") {
        while (this.nombre.isBlank()) {
            print(pedirName)
            this.nombre = readln()
            if (this.nombre == "") {
                println("*ERROR* El nombre del robot no puede estar vacío.")
            }
        }
    }

    /**
     * Solicita al usuario que introduzca el modelo del robot.
     * El número debe estar entre 0 y 3, determinando el tipo y configuración inicial del robot.
     *
     * **Diferentes modelos:**
     * - `0` → **R2D2**: Comienza en `(0, 0)` con dirección `PositiveY`.
     * - `1` → **DAW1A**: Posición inicial aleatoria en `x` (`-5` a `5`), `y = 0`, dirección `PositiveX`.
     * - `2` → **DAW1B**: Inicia en `x = 0`, `y` aleatoria entre `-10` y `10`, dirección inicial aleatoria.
     * - `3` → **DAM1**: Posición inicial aleatoria en ambos ejes (`x` e `y` entre `-5` y `5`), dirección aleatoria.
     *
     * @param pedirModelo Mensaje mostrado al usuario para ingresar el modelo del robot.
     */
    private fun obtenerModeloRobot(pedirModelo: String = "Introduce el número de modelo para el robot $nombre >> ") {
        println("""
            Modelo 0 -> RD2D
            MODELO 1 -> DAW1A
            MODELO 2 -> DAW1B
            MODELO 3 -> DAM1
        """.trimIndent())
        while (this.modeloRobot !in 0..3) {
            print(pedirModelo)
            try {
                this.modeloRobot = readln().toInt()
                if(modeloRobot !in 0..3) {
                    println("*ERROR* Ese modelo no existe.")
                }
            } catch (e: NumberFormatException) {
                println("*ERROR* No has introducido un número.")
            }
        }
    }

    /**
     * Mueve el robot en función de un array de movimientos y el tipo de robot que sea
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
            when(modeloRobot) {
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
                            direccion = if (posX > 0) Direcciones.PositiveX else Direcciones.PositiveY
                        }
                        Direcciones.NegativeY -> {
                            posY -= movimiento
                            direccion = if (posX > 0) Direcciones.NegativeX else Direcciones.PositiveX
                        }
                        Direcciones.PositiveX -> {
                            posX += movimiento
                            direccion = if (posX > 0) Direcciones.NegativeX else Direcciones.PositiveY
                        }
                    }
                }
                3 -> {
                    when(direccion) {
                        Direcciones.PositiveY -> {
                            posY += movimiento
                            direccion = generateSequence { Direcciones.obtenerDireccionRandom() }.first { it != direccion }
                        }
                        Direcciones.NegativeX -> {
                            posX -= movimiento
                            direccion = generateSequence { Direcciones.obtenerDireccionRandom() }.first { it != direccion }
                        }
                        Direcciones.NegativeY -> {
                            posY -= movimiento
                            direccion = generateSequence { Direcciones.obtenerDireccionRandom() }.first { it != direccion }
                        }
                        Direcciones.PositiveX -> {
                            posX += movimiento
                            direccion = generateSequence { Direcciones.obtenerDireccionRandom() }.first { it != direccion }
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
     * @return Una cadena que describe el nombre, posición, dirección y modelo del robot.
     */
    override fun toString(): String {
        return "ROBOT [NOMBRE: $nombre MODELO: $modeloRobot] está en ${obtenerPosicion()} ${obtenerDireccion()}"
    }
}