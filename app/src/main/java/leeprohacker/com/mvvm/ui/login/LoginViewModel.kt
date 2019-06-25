package leeprohacker.com.mvvm.ui.login

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Patterns
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


import leeprohacker.com.mvvm.R
import leeprohacker.com.mvvm.base.BaseViewModel
import leeprohacker.com.mvvm.model.TokenDao
import leeprohacker.com.mvvm.network.LoginApi
import javax.inject.Inject

class LoginViewModel(private val tokenDao: TokenDao) : BaseViewModel() {

    @Inject
    lateinit var loginApi: LoginApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private var subscription: Disposable? = null

    @SuppressLint("CheckResult")
    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job

        subscription = loginApi.login(username,password)
            .concatMap {
                if (!it.token.isNullOrEmpty()) {
                    tokenDao.insertAll(it)
                }
                Observable.just(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLoginStart() }
            .doOnTerminate { onRetrieveLoginFinish() }
            .subscribe (
                {
                    _loginResult.value = LoginResult(
                        success = LoggedInUserView(displayName = "Login Success")
                    )
                },
                {
                    _loginResult.value = if (it.message == null) LoginResult(error = R.string.login_failed) else LoginResult(
                        errorMessage = it.message
                    )
                }
            )
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5;
    }

    private fun onRetrieveLoginStart(){
        loadingVisibility.value = View.VISIBLE

    }

    private fun onRetrieveLoginFinish(){
        loadingVisibility.value = View.GONE
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }
}
