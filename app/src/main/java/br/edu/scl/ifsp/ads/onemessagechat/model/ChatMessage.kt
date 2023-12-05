package br.edu.scl.ifsp.ads.onemessagechat.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ChatMessage(
    @NonNull
    var id: String = "",
    @NonNull
    var message: String = ""
) : Parcelable