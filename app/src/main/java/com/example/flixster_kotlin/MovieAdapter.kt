package com.example.flixster_kotlin

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
        RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    //Expensive operation: create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
       return ViewHolder(view)
    }
    //Cheap: simply bind data to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       Log.i(TAG, "onBindViewHolder $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    //override fun getItemCount() = movies.size
    override fun getItemCount(): Int {
        return movies.size
    }

    //get the reference of each component in the itemView, which are one imageView, and two textView
    //and populate the data for each component by using current movie data
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            var imageUrl = movie.posterImageUrl
            var orientation = context.resources.configuration.orientation
             if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.backdropImageUrl
            }
            Glide.with(context).load(imageUrl).into(ivPoster)
        }
    }
}
