package com.example.project2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    private val APIKey = "ZCuErisFHAXcT7d1e3mdsSWTUSxibUP1"
    private lateinit var recyclerView: RecyclerView
    private var sites: List<event> = emptyList()
    private val Manager = eventDisplayManager()
    private var Adapter = eventDisplayAdapter(sites)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyc)
        lifecycleScope.launch {
            sites = withContext(IO) {
                Manager.retrieveEveryEvent(APIKey)
            }
            recyclerView.layoutManager = LinearLayoutManager(this@eventDisplay)
            //type casting error and so used many android blogs like the one I am linking: https://kotlinlang.org/docs/typecasts.html#safe-nullable-cast-operator
            (recyclerView.adapter as eventDisplayAdapter).updateDataNow(sites)
        }
    }
}