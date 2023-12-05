package br.edu.scl.ifsp.ads.onemessagechat.model

import com.google.firebase.database.FirebaseDatabase

class ChatDaoRtDbFb {

    private val databaseReference = FirebaseDatabase.getInstance().getReference("chatList")

}