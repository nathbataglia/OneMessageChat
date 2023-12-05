package br.edu.scl.ifsp.ads.onemessagechat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.onemessagechat.R
import br.edu.scl.ifsp.ads.onemessagechat.model.ChatMessage

class ChatAdapter(
    context: Context,
    private val chatList: MutableList<ChatMessage>
) : ArrayAdapter<ChatMessage>(context, R.layout.tile_message, chatList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val chatMessage = chatList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: inflater.inflate(R.layout.tile_message, parent, false)

        val idTextView: TextView = view.findViewById(R.id.idTv)
        val messageTextView: TextView = view.findViewById(R.id.messageTv)

        idTextView.text = chatMessage.id
        messageTextView.text = chatMessage.message

        return view
    }
}