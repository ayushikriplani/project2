package com.example.project2

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Favorites : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var TextView: TextView
    private lateinit var adapter: eventDisplayAdapter
    private lateinit var firebaseDatabase: FirebaseDatabase
    private val favoritesList = mutableListOf<event>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorites)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        TextView = findViewById(R.id.favTextView)
        recyclerView=findViewById(R.id.favRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = eventDisplayAdapter(favoritesList)
        recyclerView.adapter = adapter
        firebaseDatabase = FirebaseDatabase.getInstance()
        val eventDisplayManager = eventDisplayManager()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val reference = firebaseDatabase.getReference("favorites/$userId")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favorites = mutableListOf<event>()
                snapshot.children.forEach { childSnapshot ->
                    val favoriteEvent = childSnapshot.getValue(event::class.java)
                    if (favoriteEvent != null) {
                        favorites.add(favoriteEvent)
                    }
                }

                adapter.updateDataNow(favorites)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Favorites, "Failed to load favorites", Toast.LENGTH_SHORT).show()
                Log.e("FavoritesActivity", "Database error", error.toException())
            }
        })
    }
}

