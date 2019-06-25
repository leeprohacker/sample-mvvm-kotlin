package leeprohacker.com.mvvm.base

import android.arch.lifecycle.ViewModel
import leeprohacker.com.mvvm.injection.component.DaggerViewModelInjector
import leeprohacker.com.mvvm.injection.component.ViewModelInjector
import leeprohacker.com.mvvm.injection.module.NetworkModule
import leeprohacker.com.mvvm.ui.login.LoginViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }


    private fun inject() {
        when (this) {
            is LoginViewModel -> injector.inject(this)
        }
    }
}