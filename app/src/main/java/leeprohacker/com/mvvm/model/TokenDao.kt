package leeprohacker.com.mvvm.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TokenDao {
    @get:Query("SELECT * FROM token")
    val all: List<Token>

    @Insert
    fun insertAll(vararg users: Token)
}