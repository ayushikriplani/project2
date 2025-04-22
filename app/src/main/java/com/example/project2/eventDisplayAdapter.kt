package com.example.project2

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class eventDisplayAdapter(private var events: List<event>) : RecyclerView.Adapter<eventDisplayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image)
        val cardView: View = view.findViewById(R.id.card_view_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("VH", "inside onCreateViewHolder")
        val layoutInflater: LayoutInflater= LayoutInflater.from(parent.context)
        val rootLayout: View = layoutInflater.inflate(R.layout.recyclerview, parent, false)
        return ViewHolder(rootLayout)
    }

    override fun getItemCount(): Int{
        Log.d("VH", "inside counting the size of the array")
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEvent = events[position]
        holder.name.text = currentEvent.name
        if (currentEvent.urlToImage.isNotEmpty()){
            Picasso.get()
                .setIndicatorsEnabled(true)
            Picasso.get()
                .load(currentEvent.urlToImage)
                .into(holder.image)

        }
        val url= currentEvent.url

        holder.cardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(currentEvent.url)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.findViewById<ImageButton>(R.id.btnFavorite).setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val database = FirebaseDatabase.getInstance().reference
                database.child("favorites").child(user.uid).push().setValue(currentEvent)
                Toast.makeText(holder.itemView.context, "Added to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(holder.itemView.context, "You must be logged in.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateDataNow(newEvents: List<event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
