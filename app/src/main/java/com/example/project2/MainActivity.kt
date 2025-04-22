package com.example.project2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signupButton: Button
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        signupButton = findViewById(R.id.signupButton)
        loginButton = findViewById(R.id.loginButton)
        val sharedPref = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        email.setText(sharedPref.getString("email", ""))
        password.setText(sharedPref.getString("password", ""))
        loginButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val sharedPrefs = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPrefs.edit()
                        editor.putString("email", email)
                        editor.apply()
                        Toast.makeText(this, getString(R.string.loginyay), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, decide::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, getString(R.string.loginnay), Toast.LENGTH_SHORT).show()
                    }
                }
        }

        signupButton.setOnClickListener {
            val intent = Intent(this@MainActivity, signupActivity::class.java)
            startActivity(intent)
        }

    }
}