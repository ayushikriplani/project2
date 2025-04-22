package com.example.project2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class signupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirm: EditText
    private lateinit var createAccountButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        confirm = findViewById(R.id.confirmPasswordEditText)
        createAccountButton = findViewById(R.id.createAccountButton)

        createAccountButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            val confirmPassword = confirm.text.toString()
            if (password == confirmPassword) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, getString(R.string.signupyay), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, getString(R.string.signupnay), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else {
                Toast.makeText(this, getString(R.string.pwd), Toast.LENGTH_SHORT).show()
            }

        }
    }
}