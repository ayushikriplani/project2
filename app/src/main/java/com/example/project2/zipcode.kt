package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class zipcode : AppCompatActivity() {
    private lateinit var zipCode: EditText
    private lateinit var enterZipButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_zipcode)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        zipCode = findViewById(R.id.zfield)
        enterZipButton = findViewById(R.id.enter)
        enterZipButton.setOnClickListener {
            val zip = zipCode.text.toString()
            val intent = Intent(this, eventDisplay::class.java)
            intent.putExtra("ZIPCODE", zip)
            startActivity(intent)
        }

    }
}