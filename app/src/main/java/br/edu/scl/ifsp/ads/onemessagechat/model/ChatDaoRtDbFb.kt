package br.edu.scl.ifsp.ads.onemessagechat.model

import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class ChatDaoRtDbFb: ChatDao {
    companion object {
        private const val CHAT_MESSAGE_LIST_ROOT_NODE = "chatList"
    }

    private val chatMessageRtDbFbReference = Firebase.database.getReference(CHAT_MESSAGE_LIST_ROOT_NODE)

    private val chatMessageList: MutableList<ChatMessage> = mutableListOf()

    init {
        chatMessageRtDbFbReference.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage: ChatMessage? = snapshot.getValue<ChatMessage>()

                chatMessage?.also { cont ->
                    if (!chatMessageList.any { it.id_string == cont.id_string }) {
                        chatMessageList.add(cont)
                    }
                }
            }


            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage: ChatMessage? = snapshot.getValue<ChatMessage>()

                chatMessage?.also { editedChatMessage ->
                    chatMessageList.apply {
                        this[indexOfFirst { editedChatMessage.id_string == it.id_string }] = editedChatMessage
                    }
                }
            }


            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        chatMessageRtDbFbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatMessageMap = snapshot.getValue<Map<String, ChatMessage>>()

                chatMessageList.clear()
                chatMessageMap?.values?.also {
                    chatMessageList.addAll(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun createChatMessage(chatMessage: ChatMessage): Int {
        createOrUpdateChatMessage(chatMessage)
        return 1
    }

    override fun retrieveChatMessage(id_string: String): ChatMessage? {
        return chatMessageList[chatMessageList.indexOfFirst { it.id_string == id_string }]
    }

    override fun retrieveChatMessages(): MutableList<ChatMessage> = chatMessageList

    override fun updateChatMessage(chatMessage: ChatMessage): Int {
        createOrUpdateChatMessage(chatMessage)
        return 1
    }

    private fun createOrUpdateChatMessage(chatMessage: ChatMessage) =
        chatMessageRtDbFbReference.child(chatMessage.id_string.toString()).setValue(chatMessage)
}