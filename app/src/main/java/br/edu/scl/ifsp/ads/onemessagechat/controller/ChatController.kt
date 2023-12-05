package br.edu.scl.ifsp.ads.onemessagechat.controller

import br.edu.scl.ifsp.ads.onemessagechat.model.ChatDaoRtDbFb
import br.edu.scl.ifsp.ads.onemessagechat.view.MainActivity

class ChatController(private val mainActivity: MainActivity) {

    private val chatDaoImpl: ChatDaoRtDbFb = ChatDaoRtDbFb()

}