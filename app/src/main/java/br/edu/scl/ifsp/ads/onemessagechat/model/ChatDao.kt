package br.edu.scl.ifsp.ads.onemessagechat.model

interface ChatDao {
    fun createChatMessage(chatMessage: ChatMessage): Int
    fun retrieveChatMessage(id_string: String): ChatMessage?
    fun retrieveChatMessages(): MutableList<ChatMessage>
    fun updateChatMessage(chatMessage: ChatMessage): Int
}