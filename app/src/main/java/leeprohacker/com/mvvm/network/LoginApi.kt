package leeprohacker.com.mvvm.network

import io.reactivex.Observable
import leeprohacker.com.mvvm.model.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * The interface which provides methods to get result of webservices
 */
interface LoginApi {


    @FormUrlEncoded
    @POST("/v1.0/api/auth/login")
    fun login(@Field("username") email: String,@Field("password") password: String): Observable<Token>
}