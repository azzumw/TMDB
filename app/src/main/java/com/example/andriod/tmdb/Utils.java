package com.example.andriod.tmdb;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class Utils {

    private static final String LOG_TAG = Utils.class.getSimpleName();
    // --Commented out by Inspection (15/09/2020, 15:44):private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
// --Commented out by Inspection START (15/09/2020, 15:44):
//    // --Commented out by Inspection (15/09/202// --Commented out by Inspection (15/09/2020, 15:44):0, 15:44):private static final String PARAM_QUERY ="&";
//    private static final String PARAM_SORT ="sort_by";
// --Commented out by Inspection STOP (15/09/2020, 15:44)
//    private static final String sortBy ="popularity.asc";
    private static final String PARAM_PAGE = "page";
    // --Commented out by Inspection (15/09/2020, 15:44):private static final String PAGE_2 = "2";


    private Utils(){

    }

    public static String buildUrl(String url,String page){
        Uri builtUri = Uri.parse(url).buildUpon().appendQueryParameter(PARAM_PAGE,page).build();

        return builtUri.toString();
    }

    public static List<Movie> fetchMoviesData(String stringUrl){

        URL url = createUrl(stringUrl);
        Log.e(LOG_TAG,stringUrl);
        String jsonResponse = makeHttpRequest(url);

        return extractMovies(jsonResponse);
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.d(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }


        } catch (IOException e) {
            Log.d(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static java.net.URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.d(LOG_TAG,"Error with creating URL.");
            Log.e(LOG_TAG,stringUrl);
        }
        return url;
    }


    private static List<Movie> extractMovies(String jsonResponse){
        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i= 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String title = object.getString("title");
                String overView = object.getString("overview");
                String poster_path = object.getString("poster_path");

                movies.add(new Movie(title,overView,poster_path));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return movies;
    }
}
