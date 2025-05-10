package presentation.io

interface ConsoleIO {
    fun read(): String
    fun write(message: String)
    fun writeError(message: String)
}