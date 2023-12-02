data class Chat(

    var messages: MutableList<Message> = mutableListOf()
)

data class Message(
    val id: Int = 0,
    val text: String = "text",
    var read: Boolean = false,
    var delete: Boolean = false
)

fun main() {

    val message = Message(id = 1, text = "������")
    val message1 = Message(text = "������")
    val message2 = Message(text = "������", read = true)
    val service = ChatService()
    service.addMessage(1, message)
    service.addMessage(1, message1)
    service.addMessage(2, message1)
    service.addMessage(2, message2)
    service.addMessage(3, message2)
    service.deleteMessage(1, 1)
    // service.deleteMessage(1,2)

    println(service.getChats())
}
