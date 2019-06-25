package leeprohacker.com.mvvm.model.database


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import leeprohacker.com.mvvm.model.Token
import leeprohacker.com.mvvm.model.TokenDao

@Database(entities = [Token::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}