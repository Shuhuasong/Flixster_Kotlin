package com.example.flixster_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException


private const val TAG = "MainActivity";
private const val NOW_PLAY_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies : RecyclerView

    /*
     Using a RecyclerView has the following key steps:
    1. Define a model class to use as the data source (Movie class)
    2. Add a RecyclerView to your activity to display the items
    3. Create a custom row layout XML file to visualize the item
    4. Create a RecyclerView.Adapter and ViewHolder to render the item
    5. Bind the adapter to the data source to populate the RecyclerView*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieAdapter = MovieAdapter(this, movies)
        rvMovies = findViewById(R.id.rvMovies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this) //LinearLayout manager put the data in vertically(top to bottom)

        val client = AsyncHttpClient();
        client.get(NOW_PLAY_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
               Log.e(TAG, "onFailur $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) { // ? represent the null exception checking
                Log.i(TAG, "onSuccess JSON data $json")
                try{
                    val movieJsonArray = json?.jsonObject?.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "movie list $movies")
                }catch(e: JSONException){
                    Log.e(TAG, "Have Exception $e")
                }
            }

        })
    }
}