package com.example.habitgamenative.api
import com.example.habitgamenative.services.AuthResponse
import com.example.habitgamenative.services.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/signin")
    fun signIn(@Body loginRequest: LoginRequest): Call<AuthResponse>
}