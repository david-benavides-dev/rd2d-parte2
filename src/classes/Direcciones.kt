package classes

enum class Direcciones {
    PositiveX, NegativeX, PositiveY, NegativeY;

    companion object {
        fun obtenerDireccionRandom(): Direcciones {
            return Direcciones.entries.random()
        }
    }
}