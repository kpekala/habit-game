package com.example.habitgamenative.api
import com.example.habitgamenative.services.AddTaskRequest
import com.example.habitgamenative.services.AuthResponse
import com.example.habitgamenative.services.LoginRequest
import com.example.habitgamenative.services.TasksResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

data class FinishTaskRequest (val taskId: Int)
data class FinishTaskResponse (val leveledUp: Boolean, val currentLevel: Int)

interface Api {
    @POST("auth/signin")
    fun signIn(@Body loginRequest: LoginRequest): Call<AuthResponse>

    @GET("task")
    fun fetchTasks(@Query("email") email: String, @Header("Authorization") token: String): Call<TasksResponse>

    @POST("task/finish")
    fun finishTask(@Body request: FinishTaskRequest, @Header("Authorization") token: String): Call<FinishTaskResponse>

    @POST("task/add")
    fun addTask(@Body request: AddTaskRequest, @Header("Authorization") token: String): Call<Void>

    @Multipart
    @POST("task/upload-photo")
    fun uploadPhoto(
        @Part file: MultipartBody.Part,
        @Part("photoId") photoId: RequestBody,
        @Header("Authorization") token: String
    ): Call<Void>
}