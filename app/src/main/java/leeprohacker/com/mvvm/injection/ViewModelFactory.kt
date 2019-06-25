package leeprohacker.com.mvvm.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import leeprohacker.com.mvvm.model.database.AppDatabase
import leeprohacker.com.mvvm.ui.login.LoginViewModel
import java.security.AccessControlContext


class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "login").build()
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(db.tokenDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}