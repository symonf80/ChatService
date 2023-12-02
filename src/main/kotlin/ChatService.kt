import java.util.stream.Collectors.toMap

class ChatService {

    var chats = mutableMapOf<Int, Chat>()
    private val alert = "Нет сообщений"
    fun addMessage(id: Int, message: Message) {
        chats.getOrPut(id) { Chat() }.messages += message
    }

    fun getChats(): List<Chat> {
        return chats.map { it.value }
    }

    fun deleteChat(id: Int) {
        val chat = chats[id] ?: throw ChatNotFoundException("Чатов нет")
        chat.messages.removeAll { _ -> true }
        chat.messages += Message(text = "Данный чат удален", delete = true)
        chats[id] = chat
    }

    fun deleteMessage(id: Int, number: Int) {
        val chat = chats[id] ?: throw ChatNotFoundException(alert)
        chat.messages.filter { !it.delete }.find { it.id == number }?.apply { this.delete = true }
        chats[id] = chat
    }

    fun getLastMessages(): List<String> {
        return chats.values.map { chat -> chat.messages.lastOrNull { !it.delete }?.text ?: alert }
    }

    fun getChatsNotRead(): List<Int> {
        return chats.values.map { chat -> chat.messages.filter { !it.read }.size }

    }

    fun getMessages(id: Int, count: Int): List<Message> {
        val chat = chats[id] ?: throw ChatNotFoundException(alert)
        return chat.messages.filter { !it.delete }.takeLast(count).onEach { it.read = true }
    }
}

class ChatNotFoundException(message: String) : RuntimeException(message)