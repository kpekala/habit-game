package com.example.habitgamenative.services

import android.content.SharedPreferences
import android.util.Log
import com.example.habitgamenative.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class Task(
    val title: String,
    val description: String,
    val difficulty: String,
    val date: String,
    val id: Int,
    val completed: Boolean,
    val location: Location,
    val photoId: String
)
data class Habit(val name: String)

data class Location(val latitude: Float, val longitude: Float, val place: String)

data class TasksResponse(
    val tasks: List<Task>
)


interface TasksListener {
    fun onGetTasksSuccess(tasks: List<Task>)
}

interface HabitsListener {
    fun onGetHabitsSuccess(habits: List<Habit>)
}

class TasksService(private val sharedPreferences: SharedPreferences) {

    fun fetchTasks(tasksListener: TasksListener) {

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
}