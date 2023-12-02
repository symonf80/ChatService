import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    private val service = ChatService()

    @Test
    fun addMessage() {
        val result = service.addMessage(1, Message())
        assertNotNull(result)
    }

    @Test
    fun getChats() {
        service.addMessage(1, Message())
        service.addMessage(2, Message())
        val result = service.getChats().size
        assertEquals(2, result)
    }


    @Test
    fun deleteChat() {
        service.addMessage(1, Message())
        service.addMessage(2, Message())
        service.deleteChat(2)
        val result = service.chats.getValue(2).messages[0].delete
        assertTrue(result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun notDeleteChat() {
        val result = service.deleteChat(1)
    }

    @Test
    fun deleteMessage() {
        service.addMessage(1, Message(id = 1))
        service.addMessage(1, Message())
        service.deleteMessage(1, 1)
        val result = service.chats.getValue(1).messages[0].delete
        assertTrue(result)
    }


    @Test(expected = ChatNotFoundException::class)
    fun notDeleteMessage() {
        val result = service.deleteMessage(1, 1)
    }

    @Test
    fun getLastMessages() {
        service.addMessage(1, Message())
        val result = service.getLastMessages().size
        assertEquals(1, result)
    }

    @Test
    fun getLastMessagesIfDelete() {
        service.addMessage(1, Message(id = 1))
        service.deleteMessage(1, 1)
        val result = service.getLastMessages().joinToString()
        assertEquals("Нет сообщений", result)
    }

    @Test
    fun getChatsNotRead() {
        service.addMessage(1, Message(read = true))
        val result = service.getChatsNotRead().last()
        assertEquals(0, result)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getMessagesException() {
        service.addMessage(1, Message())
        val result = service.getMessages(4, 1)
    }

    @Test
    fun getMessage() {
        service.addMessage(1, Message())
        service.addMessage(2, Message())
        val result = service.getMessages(1, 1).size
        assertEquals(1, result)
    }
}