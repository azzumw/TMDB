package com.example.andriod.tmdb;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListItemClickListener{

    protected MovieAdapter movieAdapter;

    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    MovieAsyncTask movieAsyncTask;



    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=51ed01ec1db0ac9a518638cb27934aec&language=en-US&page=1";
    private static final String BASE_URL_2 = "https://api.themoviedb.org/3/movie/top_rated?api_key=51ed01ec1db0ac9a518638cb27934aec&language=en-US&page=1";

//    private static final String URL = "https://api.themoviedb.org/3/discover/movie?api_key=51ed01ec1db0ac9a518638cb27934aec&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=true&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_list);

       //set Layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);

        List<Movie>  movie = new ArrayList<>();
        movieAdapter = new MovieAdapter(movie,this);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this );

        boolean name = sharedPreferences.getBoolean("h_rated",false);

        String page = sharedPreferences.getString(getString(R.string.ET_KEY_PREF),getString(R.string.DEFAULT_PREF_VALUE));
        Log.e(LOG_TAG,page + "  PAGE");

        movieAsyncTask = new MovieAsyncTask();
        if(name){
            movieAsyncTask.execute(BASE_URL_2,page);
        }else {
            movieAsyncTask.execute(BASE_URL,page);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.option_more){
            //make a url query to page 2
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClickListener(int index, Movie movie) {
        Intent intent = new Intent(this,DetailActivity.class);

        intent.putExtra("movie",movie);
        startActivity(intent);

    }

    private  class MovieAsyncTask extends AsyncTask<String,Void,List<Movie>>{

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String url = strings[0];
            String page = strings[1];

            return Utils.fetchMoviesData(Utils.buildUrl(url,page));
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieAdapter.addAll(movies);
            movieAdapter.notifyDataSetChanged();
        }
    }
}