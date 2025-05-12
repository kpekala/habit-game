package com.example.habitgamenative.game

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitgamenative.R
import com.example.habitgamenative.services.Task
import com.example.habitgamenative.services.GetTasksListener
import com.example.habitgamenative.services.TasksService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class TasksFragment : Fragment(), GetTasksListener {

    private lateinit var listTasks: RecyclerView
    private lateinit var listHabits: RecyclerView
    private lateinit var buttonAddTask: Button

    private lateinit var imageViewAddTask: ImageView
    private lateinit var textViewLocation: TextView


    private lateinit var tasksService: TasksService
    private val tasks = ArrayList<Task>()

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            imageViewAddTask.visibility = View.VISIBLE
            imageViewAddTask.setImageURI(uri)
        }
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksService = TasksService(view.context.getSharedPreferences(
            getString(R.string.shared_prefs), Context.MODE_PRIVATE))
        initLists(view)
        initButtons(view)
        tasksService.fetchTasks(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1001
        )
    }

    private fun initButtons(view: View) {
        buttonAddTask = view.findViewById(R.id.buttonAddTask)

        buttonAddTask.setOnClickListener {
            openAddTaskDialog()
        }
    }

    private fun openAddTaskDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_task, null)

        dialogView.findViewById<Button>(R.id.btnAddImage)
            .setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        dialogView.findViewById<Button>(R.id.btnGetLocation)
            .setOnClickListener {
                getLocation()
            }
        textViewLocation = dialogView.findViewById(R.id.textLocation)
        imageViewAddTask = dialogView.findViewById(R.id.imageViewAddTask)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Add", { dialog, id ->
                onAddTaskClick(dialogView)
            })
            .setNegativeButton("Cancel", { dialog, id ->

            })
            .create()
        dialog.show()



    }

    private fun onAddTaskClick(dialogView: View?) {

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

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                textViewLocation.text = "$latitude $longitude"
            } else {
                Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_SHORT).show()
            }
        }
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