package leeprohacker.com.mvvm.injection.component

import dagger.Component
import leeprohacker.com.mvvm.injection.module.NetworkModule
import leeprohacker.com.mvvm.ui.login.LoginViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(loginViewModel: LoginViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}