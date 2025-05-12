package com.example.habitgamenative.services

import android.content.SharedPreferences
import android.util.Log
import com.example.habitgamenative.api.FinishTaskRequest
import com.example.habitgamenative.api.FinishTaskResponse
import com.example.habitgamenative.api.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

data class Task(
    val title: String,
    val description: String,
    val difficulty: String,
    val date: String,
    val id: Int,
    val completed: Boolean,
    val location: TaskLocation,
    val photoId: String
)
data class Habit(val name: String)

data class TaskLocation(val latitude: Float, val longitude: Float, val place: String)

data class TasksResponse(
    val tasks: List<Task>
)

data class AddTaskRequest(
    var title: String,
    var description: String,
    var difficulty: String,
    var deadline: String,
    var email: String,
    var location: TaskLocation?,
    var photoId: String?
)

interface GetTasksListener {
    fun onGetTasksSuccess(tasks: List<Task>)
}

interface HabitsListener {
    fun onGetHabitsSuccess(habits: List<Habit>)
}

class TasksService(private val sharedPreferences: SharedPreferences) {

    fun fetchTasks(tasksListener: GetTasksListener) {

        val email = sharedPreferences.getString("email", "") ?: return
        var token = sharedPreferences.getString("token", "") ?: return
        token = "Bearer $token"

        RetrofitClient.instance.fetchTasks(email, token).enqueue(object : Callback<TasksResponse> {
            override fun onResponse(
                call: Call<TasksResponse>,
                response: Response<TasksResponse>
            ) {
                if (response.isSuccessful) {
                    val tasks = response.body()?.tasks.orEmpty()
                    tasksListener.onGetTasksSuccess(tasks)
                } else {
                    Log.e("API", "Failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TasksResponse>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
            }
        })
    }

    fun finishTask(taskId: Int, onSuccess: (response: FinishTaskResponse) -> Unit) {
        var token = sharedPreferences.getString("token", "") ?: return
        token = "Bearer $token"

        RetrofitClient.instance.finishTask(FinishTaskRequest(taskId), token)
            .enqueue(object : Callback<FinishTaskResponse> {
                override fun onResponse(
                    call: Call<FinishTaskResponse>,
                    response: Response<FinishTaskResponse>
                ) {
                    if (response.isSuccessful) {
                        onSuccess(response.body()!!)
                    } else {
                        Log.e("API", "Failed: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<FinishTaskResponse>, t: Throwable) {
                    Log.e("API", "Error: ${t.message}")
                }
            })
    }

    fun addTask(request: AddTaskRequest, onSuccess: () -> Unit) {
        var token = sharedPreferences.getString("token", "") ?: return
        token = "Bearer $token"
        request.email = sharedPreferences.getString("email", "") ?: return

        RetrofitClient.instance.addTask(request, token)
            .enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        Log.e("API", "Failed: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("API", "Error: ${t.message}")
                }
            })
    }

    fun uploadPhoto(imageFile: File, photoId: String, onSuccess: () -> Unit) {
        var token = sharedPreferences.getString("token", "") ?: return
        token = "Bearer $token"
        val requestFile = imageFile
            .asRequestBody("image/*".toMediaTypeOrNull())

        val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
        val idBody = photoId.toRequestBody("text/plain".toMediaTypeOrNull())

        RetrofitClient.instance.uploadPhoto(body, idBody, token)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("Upload", "Success: ${response.body()}")
                    } else {
                        Log.e("Upload", "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("Upload", "Failure: ${t.message}")
                }
            })
    }
}