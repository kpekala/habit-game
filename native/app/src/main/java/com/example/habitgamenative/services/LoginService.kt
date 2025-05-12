package com.example.habitgamenative.services

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.habitgamenative.api.RetrofitClient
import retrofit2.Call

data class LoginRequest(
    val emailAddress: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val tokenExpirationDate: String
)

interface LoginListener {
    fun onSuccess(response: AuthResponse)
    fun onError(msg: String)
}

class LoginService(var sharedPreferences: SharedPreferences) {

    fun login(emailAddress: String, password: String, loginListener: LoginListener) {
        val loginRequest = LoginRequest(emailAddress, password)

        RetrofitClient.instance.signIn(loginRequest).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: retrofit2.Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    sharedPreferences.edit {
                        putString("token", authResponse?.token)
                        putString("date", authResponse?.tokenExpirationDate)
                        putString("email", emailAddress)
                    }
                    println("Token: ${authResponse?.token}")
                    println("date: ${authResponse?.tokenExpirationDate}")
                    loginListener.onSuccess(authResponse!!)
                } else {
                    println("Error: ${response.code()}")
                    loginListener.onError(response.code().toString())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}