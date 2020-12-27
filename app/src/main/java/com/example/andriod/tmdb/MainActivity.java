package com.example.andriod.tmdb;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListItemClickListener, LoaderManager.LoaderCallbacks<List<Movie>> {


    private static final int UNIQUE_LOADER_ID = 22;
    protected MovieAdapter movieAdapter;
    private LoaderManager loaderManager;
    Loader<Movie> movieLoader;
    GridLayoutManager gridLayoutManager;
    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
//    MovieAsyncTask movieAsyncTask;



//    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=51ed01ec1db0ac9a518638cb27934aec&language=en-US&page=1";
//    private static final String BASE_URL_2 = "https://api.themoviedb.org/3/movie/top_rated?api_key=51ed01ec1db0ac9a518638cb27934aec&language=en-US&page=1";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";

//    private static final String URL = "https://api.themoviedb.org/3/discover/movie?api_key=51ed01ec1db0ac9a518638cb27934aec&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=true&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_list);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
             gridLayoutManager = new GridLayoutManager(this,4);
        }
       //set Layout manager
         gridLayoutManager = new GridLayoutManager(this,3);


        List<Movie>  movie = new ArrayList<>();
        movieAdapter = new MovieAdapter(movie,this);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

//        SharedPreferences sharedPreferences =
//                PreferenceManager.getDefaultSharedPreferences(this );
//
//        boolean name = sharedPreferences.getBoolean("h_rated",false);
//
//        String page = sharedPreferences.getString(getString(R.string.ET_KEY_PREF),getString(R.string.DEFAULT_PREF_VALUE));
//        Log.e(LOG_TAG,page + "  PAGE");

//        loaderManager = getSupportLoaderManager();
//        Loader<Movie> movieLoader = loaderManager.getLoader(UNIQUE_LOADER_ID);
//        if (movieLoader == null) {
//            getSupportLoaderManager().initLoader(UNIQUE_LOADER_ID,null,this);
//        } else {
//            getSupportLoaderManager().restartLoader(UNIQUE_LOADER_ID, null, this);
//        }


//        movieAsyncTask = new MovieAsyncTask();
//        if(name){
//            movieAsyncTask.execute(BASE_URL_2,page);
//        }else {
//            movieAsyncTask.execute(BASE_URL,page);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getSupportLoaderManager().restartLoader(UNIQUE_LOADER_ID, null, this);
        getSupportLoaderManager().initLoader(UNIQUE_LOADER_ID,null,this).forceLoad();
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
    public void onListItemClickListener( Movie movie) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("movie",movie);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this );

        boolean name = sharedPreferences.getBoolean("h_rated",false);

        String page = sharedPreferences.getString(getString(R.string.ET_KEY_PREF),getString(R.string.DEFAULT_PREF_VALUE));

        String endpoint = name? "top_rated":"popular";



        return new MovieAsyncTaskLoader(this,BASE_URL,endpoint,page);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {

        //copy code on onPostExecute
        //set the visibility of loading indicator to invisible
        Log.e(LOG_TAG, "FinishedLoading");
        if (data != null && !data.isEmpty()) {
            Log.e(LOG_TAG, "addingData");
            movieAdapter.addAll(data);
            for (Movie x: data) {
                Log.e(LOG_TAG, x.getMovieName());
            }
//            Log.e(LOG_TAG, data.get(5).getMovieName());
        }
//        movieAdapter.addAll(data);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
//        movieAdapter.clear();
    }



    private static class MovieAsyncTaskLoader extends AsyncTaskLoader<List<Movie>>{

        private String url;
        private String pageNumber;
        private String endpoint;
        private List<Movie> movieList;

        public MovieAsyncTaskLoader(@NonNull Context context, String url,String endpoint,String page) {
            super(context);
            this.url = url;
            this.pageNumber = page;
            this.endpoint = endpoint;
        }

        @Override
        public List<Movie> loadInBackground() {
            Log.e(LOG_TAG, "LoadinBackground");
            List movies = Utils.fetchMoviesData(Utils.buildUrl(url,endpoint,pageNumber));
            return movies;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
//            forceLoad();

            Log.e(LOG_TAG, "startLoading");
//            if(movieList!=null){
//                deliverResult(movieList);
//            }

//            if(==null){ return;}
            //here you set loading indicator visbility to visible
        }

//        @Override
//        public void deliverResult(@Nullable List<Movie> data) {
//
//            super.deliverResult(data);
//            movieList = data;
//        }
    }


//    private  class MovieAsyncTask extends AsyncTask<String,Void,List<Movie>>{
//
//        @Override
//        protected List<Movie> doInBackground(String... strings) {
//            String url = strings[0];
//            String page = strings[1];
//
//            return Utils.fetchMoviesData(Utils.buildUrl(url,page));
//        }
//
//        @Override
//        protected void onPostExecute(List<Movie> movies) {
//            movieAdapter.addAll(movies);
//            movieAdapter.notifyDataSetChanged();
//        }
//    }
}