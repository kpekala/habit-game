package com.example.habitgamenative

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitgamenative.services.AuthResponse
import com.example.habitgamenative.services.LoginListener
import com.example.habitgamenative.services.LoginService

class LoginActivity : Activity(), LoginListener {

    private lateinit var loginButton: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginService: LoginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginService = LoginService(getSharedPreferences(
            getString(R.string.shared_prefs), Context.MODE_PRIVATE))
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loginButton = findViewById(R.id.loginButton)
        emailInput = findViewById(R.id.editTextTextEmailAddress)
        passwordInput = findViewById(R.id.editTextTextPassword)

        setupListeners()
    }

    private fun setupListeners() {
        loginButton.setOnClickListener({
            loginService.login(emailInput.text.toString(), passwordInput.text.toString(), this)
        })
    }

    override fun onSuccess(response: AuthResponse) {

    }

    override fun onError(msg: String) {
        TODO("Not yet implemented")
    }
}