package br.edu.scl.ifsp.ads.onemessagechat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.scl.ifsp.ads.onemessagechat.adapter.ChatAdapter
import br.edu.scl.ifsp.ads.onemessagechat.controller.ChatController
import br.edu.scl.ifsp.ads.onemessagechat.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.onemessagechat.model.ChatMessage

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val chatList: MutableList<ChatMessage> = mutableListOf()

    // Controller
    private val chatController: ChatController by lazy {
        ChatController(this)
    }

    // Adapter
    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter(
            this,
            chatList
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)

        amb.chatLv.adapter = chatAdapter

        //chatController.getChats()
    }

    fun updateChatList(_chatList: MutableList<ChatMessage>) {
        chatList.clear()
        chatList.addAll(_chatList)
        chatAdapter.notifyDataSetChanged()
    }
}