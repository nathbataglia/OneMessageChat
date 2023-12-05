package br.edu.scl.ifsp.ads.onemessagechat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import br.edu.scl.ifsp.ads.onemessagechat.R
import br.edu.scl.ifsp.ads.onemessagechat.adapter.ChatAdapter
import br.edu.scl.ifsp.ads.onemessagechat.controller.ChatControllerRtDbFb
import br.edu.scl.ifsp.ads.onemessagechat.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.onemessagechat.model.ChatMessage
import br.edu.scl.ifsp.ads.onemessagechat.model.Constant.MESSAGE_ARRAY

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val chatList: MutableList<ChatMessage> = mutableListOf()

    // Controller
    private val chatController: ChatControllerRtDbFb by lazy {
        ChatControllerRtDbFb(this)
    }

    // Adapter
    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter(
            this,
            chatList
        )
    }

    companion object {
        const val GET_CHAT_MESSAGES_MSG = 1
        const val GET_CHAT_MESSAGES_INTERVAL = 2000L
    }

    //Handler
    val updateChatListHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message){
            super.handleMessage(msg)

            if(msg.what == GET_CHAT_MESSAGES_MSG){
                chatController.getChatMessages()
                sendMessageDelayed(obtainMessage().apply { what = GET_CHAT_MESSAGES_MSG }, GET_CHAT_MESSAGES_INTERVAL)
            } else{
                msg.data.getParcelableArray(MESSAGE_ARRAY)?.also { contactArray ->
                    chatList.clear()
                    contactArray.forEach {
                        chatList.add(it as ChatMessage)
                    }
                    chatAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private lateinit var carl: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)

        amb.chatLv.adapter = chatAdapter

        //chatController.getChats()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addMessageMi -> {
                carl.launch(Intent(this, ChatMessageActivity::class.java))
                true
            }
            else -> false
        }
    }


    fun updateChatList(_chatList: MutableList<ChatMessage>) {
        chatList.clear()
        chatList.addAll(_chatList)
        chatAdapter.notifyDataSetChanged()
    }
}