package br.edu.scl.ifsp.ads.onemessagechat.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.onemessagechat.R
import br.edu.scl.ifsp.ads.onemessagechat.databinding.TileMessageBinding
import br.edu.scl.ifsp.ads.onemessagechat.model.ChatMessage

class ChatAdapter(
    context: Context,
    private val chatList: MutableList<ChatMessage>
) : ArrayAdapter<ChatMessage>(context, R.layout.tile_message, chatList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val chatMessage = chatList[position]
        var tmb: TileMessageBinding? = null

        var chatMessageTileView = convertView
        if (chatMessageTileView == null) {
            // então precisa inflar a célula
            tmb = TileMessageBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            chatMessageTileView = tmb.root

            val tileChatMessageHolder = TileChatMessageHolder(tmb.idStringTv, tmb.messageTv)
            chatMessageTileView.tag = tileChatMessageHolder
        }

        (chatMessageTileView.tag as TileChatMessageHolder).idStringTv.setText(chatMessage.id_string)
        (chatMessageTileView.tag as TileChatMessageHolder).messageTv.setText(chatMessage.message)

        tmb?.idStringTv?.setText(chatMessage.id_string)
        tmb?.messageTv?.setText(chatMessage.message)

        return chatMessageTileView
    }

    private data class TileChatMessageHolder(val idStringTv: TextView, val messageTv: TextView)
}

