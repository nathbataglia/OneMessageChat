package br.edu.scl.ifsp.ads.onemessagechat.model

import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class ChatDaoRtDbFb: ChatDao {
    companion object {
        private const val CONTACT_LIST_ROOT_NODE = "chatList"
    }

    private val chatMessageRtDbFbReference = Firebase.database.getReference(CONTACT_LIST_ROOT_NODE)

    private val chatMessageList: MutableList<ChatMessage> = mutableListOf()

    init {
        chatMessageRtDbFbReference.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val contact: ChatMessage? = snapshot.getValue<ChatMessage>()

                contact?.also { cont ->
                    if (!chatMessageList.any { it.id == cont.id }) {
                        chatMessageList.add(cont)
                    }
                }
            }


            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val contact: ChatMessage? = snapshot.getValue<ChatMessage>()

                contact?.also { editedChatMessage ->
                    chatMessageList.apply {
                        this[indexOfFirst { editedChatMessage.id == it.id }] = editedChatMessage
                    }
                }
            }


            override fun onChildRemoved(snapshot: DataSnapshot) {
                val contact: ChatMessage? = snapshot.getValue<ChatMessage>()

                contact?.also {
                    chatMessageList.remove(it)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                //NSA
            }

            override fun onCancelled(error: DatabaseError) {
                //NSA
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
                //NSA
            }
        })
    }

    override fun createChatMessage(chatMessage: ChatMessage): Int {
        createOrUpdateChatMessage(chatMessage)
        return 1
    }

    override fun retrieveChatMessage(id: Int): ChatMessage? {
        return chatMessageList[chatMessageList.indexOfFirst { it.id == id }]
    }

    override fun retrieveChatMessages(): MutableList<ChatMessage> = chatMessageList

    override fun updateChatMessage(chatMessage: ChatMessage): Int {
        createOrUpdateChatMessage(chatMessage)
        return 1
    }

    private fun createOrUpdateChatMessage(chatMessage: ChatMessage) =
        chatMessageRtDbFbReference.child(chatMessage.id.toString()).setValue(chatMessage)
}