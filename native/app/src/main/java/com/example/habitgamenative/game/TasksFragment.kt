package com.example.habitgamenative.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitgamenative.R
import com.example.habitgamenative.services.Task
import com.example.habitgamenative.services.GetTasksListener
import com.example.habitgamenative.services.TasksService

class TasksFragment : Fragment(), GetTasksListener {

    private lateinit var listTasks: RecyclerView
    private lateinit var listHabits: RecyclerView
    private lateinit var tasksService: TasksService

    private val tasks = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksService = TasksService(view.context.getSharedPreferences(
            getString(R.string.shared_prefs), Context.MODE_PRIVATE))
        initLists(view)

        tasksService.fetchTasks(this)
    }

    private fun openTaskDialog(position: Int) {
        val task = tasks[position]
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_task, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Finish", { dialog, id ->
                tasksService.finishTask(position, {response ->  onTaskFinished(position)})
            })
            .setNegativeButton("Cancel", { dialog, id ->

            })
            .create()

        // Bind views
        val textTitle = dialogView.findViewById<TextView>(R.id.textTitle)
        val taskDescription = dialogView.findViewById<TextView>(R.id.taskDescription)
        val taskDifficulty = dialogView.findViewById<TextView>(R.id.taskDifficulty)
        val taskLocation = dialogView.findViewById<TextView>(R.id.taskLocation)
        val taskImage = dialogView.findViewById<ImageView>(R.id.taskImage)

        // Set content
        textTitle.text = task.title
        taskDescription.text = task.description
        taskDifficulty.text = "Difficulty: ${task.difficulty}"
        taskLocation.text = "Location: ${task.location.latitude} ${task.location.longitude}"

        if(task.photoId != null && task.photoId.isNotEmpty()) {
            taskImage.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load("http://10.0.2.2:8080/api/task/uploads/${task.photoId}")
                .into(taskImage)
        }
        dialog.show()
    }

    private fun onTaskFinished(position: Int) {
        tasksService.fetchTasks(this)
        Toast.makeText(this.requireContext(), "Task finished successfully!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun initLists(view: View) {
        listTasks = view.findViewById(R.id.listTasks)
        listTasks.layoutManager = LinearLayoutManager(view.context)
        listTasks.adapter = TextListAdapter(listOf()) { position: Int ->
            openTaskDialog(position)
        }

        listHabits = view.findViewById(R.id.listHabits)
        listHabits.layoutManager = LinearLayoutManager(view.context)
        listHabits.adapter = TextListAdapter(listOf(), {position: Int -> {
        }})
    }

    override fun onGetTasksSuccess(tasks: List<Task>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)

        val adapter = listTasks.adapter
        if(adapter is TextListAdapter) {
            adapter.updateData(tasks.map { task -> task.title })
        }
    }
}