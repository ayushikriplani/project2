package com.example.project2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class eventDisplay : AppCompatActivity() {
    val APIKey = "kONZlxcGmOsPJfTCm71yisQY5wjhkVfz"
    private lateinit var recyclerView: RecyclerView
    private lateinit var TextView : TextView
    private lateinit var adapter: eventDisplayAdapter
    private val manager = eventDisplayManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_display)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        TextView = findViewById(R.id.newevent)
        recyclerView = findViewById(R.id.recyc)
        adapter = eventDisplayAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        //defaults to zipcode 10001 if no zipcode is provided
        val zip = intent.getStringExtra("ZIPCODE") ?: "10001"

        lifecycleScope.launch {
            val events = withContext(IO) {
                manager.retrieveEveryEvent(APIKey, zip)
            }
            adapter.updateDataNow(events)
        }
    }
}
