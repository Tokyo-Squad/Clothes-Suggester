package presentation.io

class ConsoleIOImpl : ConsoleIO {
    companion object {
        private const val ANSI_RESET = "\u001B[0m"
        private const val ANSI_RED = "\u001B[31m"
    }
    override fun read(): String = readlnOrNull()?.trim() ?: ""

    override fun write(message: String) {
        println(message)
    }
    override fun writeError(message: String) {
        println("$ANSI_RED$message$ANSI_RESET")
    }
}