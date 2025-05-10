package presentation.io

class ConsoleIOImpl : ConsoleIO {

    override fun read(): String = readlnOrNull()?.trim() ?: ""

    override fun write(message: String) {
        println(message)
    }
}