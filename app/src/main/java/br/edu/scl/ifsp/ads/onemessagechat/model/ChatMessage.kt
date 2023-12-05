package br.edu.scl.ifsp.ads.onemessagechat.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.scl.ifsp.ads.onemessagechat.model.Constant.INVALID_MESSAGE_ID
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = INVALID_MESSAGE_ID,
    @NonNull
    var id_string: String = "",
    @NonNull
    var message: String = ""
) : Parcelable