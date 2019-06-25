package leeprohacker.com.mvvm.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class Token(
    val access_token: String,
    @field:PrimaryKey(autoGenerate = true)
    val id: Int,
    val refresh_expires_in: Int,
    val refresh_token: String,
    val token_type: String,
    val id_token: String,
    val session_state: String,
    val token: String
)