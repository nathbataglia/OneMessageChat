package br.edu.scl.ifsp.ads.onemessagechat.controller

import br.edu.scl.ifsp.ads.onemessagechat.model.ChatDaoRtDbFb
import br.edu.scl.ifsp.ads.onemessagechat.model.ChatMessage
import br.edu.scl.ifsp.ads.onemessagechat.model.Constant
import br.edu.scl.ifsp.ads.onemessagechat.view.MainActivity

class ChatControllerRtDbFb(private val mainActivity: MainActivity) {

    private val chatDaoImpl: ChatDaoRtDbFb = ChatDaoRtDbFb()

    fun insertChatMessage(chatMessage: ChatMessage) {
        Thread {
            chatDaoImpl.createChatMessage(chatMessage)
        }.start()
    }

    fun getChatMessage(id_string: String) = chatDaoImpl.retrieveChatMessage(id_string)

    fun getChatMessages() {
        Thread{
            mainActivity.updateChatListHandler.apply {
                sendMessage(
                    obtainMessage().apply {
                        data.putParcelableArray(
                            Constant.MESSAGE_ARRAY,
                            chatDaoImpl.retrieveChatMessages().toTypedArray()
                        )
                    }
                )
            }
        }.start()
    }

    fun editChatMessage(chatMessage: ChatMessage){
        Thread {
            chatDaoImpl.updateChatMessage(chatMessage)
            getChatMessages()
        }.start()
    }
}