package com.example.project2

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class eventDisplayAdapter (private var new: List<event>): RecyclerView.Adapter<eventDisplayAdapter.ViewHolder>() {
    class ViewHolder(rootLayout: View): RecyclerView.ViewHolder(rootLayout) {
        val name: TextView=rootLayout.findViewById(R.id.name)
        val image: ImageView=rootLayout.findViewById(R.id.image)
        val cardView: View=rootLayout.findViewById(R.id.card_view_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("VH", "inside onCreateViewHolder")
        val layoutInflater: LayoutInflater= LayoutInflater.from(parent.context)
        val rootLayout: View = layoutInflater.inflate(R.layout.recyclerview, parent, false)
        return ViewHolder(rootLayout)
    }

    override fun getItemCount(): Int {
        Log.d("VH", "inside counting the size of the array")
        return new.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTopNews=new[position]
        holder.name.text= currentTopNews.name
        if (currentTopNews.urlToImage.isNotEmpty()){
            Picasso.get()
                .setIndicatorsEnabled(true)
            Picasso.get()
                .load(currentTopNews.urlToImage)
                .into(holder.image)

        }
        val url= currentTopNews.url
        holder.cardView.setOnClickListener{
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse(url)
            holder.itemView.context.startActivity(intent)
        }
        Log.d("VH", "inside onBindViewHolder on position $position")
    }
    fun updateDataNow(newSites: List<event>) {
        new = newSites
        //source: https://stackoverflow.com/questions/3669325/notifydatasetchanged-example
        notifyDataSetChanged()
    }
}