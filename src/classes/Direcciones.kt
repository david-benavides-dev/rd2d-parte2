package classes

enum class Direcciones {
    PositiveX, NegativeX, PositiveY, NegativeY;

    companion object {
        val DIRECCIONES = entries.toTypedArray()
        val direccionRandom = Direcciones.DIRECCIONES.random()
    }
}