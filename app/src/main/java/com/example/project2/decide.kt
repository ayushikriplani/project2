package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class decide : AppCompatActivity() {
    private lateinit var exploreButton: Button
    private lateinit var favoritesButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_decide)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        exploreButton = findViewById(R.id.explore)
        favoritesButton = findViewById(R.id.favorites)
        exploreButton.setOnClickListener {
            val intent = Intent(this@decide, zipcode::class.java)
            startActivity(intent)
        }
        favoritesButton.setOnClickListener {
            val intent = Intent(this@decide, Favorites::class.java)
            startActivity(intent)
        }



    }
}