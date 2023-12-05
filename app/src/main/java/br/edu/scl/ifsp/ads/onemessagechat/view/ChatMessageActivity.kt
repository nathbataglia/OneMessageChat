package br.edu.scl.ifsp.ads.onemessagechat.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.onemessagechat.databinding.ActivityMessageBinding
import br.edu.scl.ifsp.ads.onemessagechat.model.ChatMessage
import br.edu.scl.ifsp.ads.onemessagechat.model.Constant.EXTRA_MESSAGE
import br.edu.scl.ifsp.ads.onemessagechat.model.Constant.VIEW_MESSAGE

class ChatMessageActivity : AppCompatActivity() {
    private val acmb: ActivityMessageBinding by lazy {
        ActivityMessageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acmb.root)

        setSupportActionBar(acmb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Message Details"

        val receivedMessage = intent.getParcelableExtra<ChatMessage>(EXTRA_MESSAGE)
        receivedMessage?.let { _receivedMessage ->
            val viewMessage = intent.getBooleanExtra(VIEW_MESSAGE, false)
            with (acmb) {
                if(viewMessage) {
                    idStringEt.isEnabled = false
                    messageEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
                idStringEt.setText(_receivedMessage.id_string)
                messageEt.setText(_receivedMessage.message)
            }
        }

        with (acmb) {
            saveBt.setOnClickListener {
                val message = ChatMessage(
                    // Parâmetro nomeado
                    id_string = idStringEt.text.toString(),
                    message = messageEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_MESSAGE, message)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }


}