package com.example.andriod.tmdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    final static String INTENT_KEY = "movie";
    String baseImagePath ="http://image.tmdb.org/t/p/w185/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        TextView overView = findViewById(R.id.overview_tv);
        ImageView imageView = findViewById(R.id.imageView);
        TextView releaseDate = findViewById(R.id.release_date_tv);
        TextView rating = findViewById(R.id.user_rating_tv);

        Intent intent = getIntent();

        if(intent.hasExtra(INTENT_KEY)){
            Movie movie = intent.getExtras().getParcelable(INTENT_KEY);
            if(movie!=null)
                setTitle(movie.getMovieName());
                overView.setText(movie.getOverView());
                releaseDate.setText(movie.getReleaseDate());
                rating.setText(String.valueOf(movie.getVote_avg()));
                String currentImagePath = movie.getImage();
            Picasso.get().load(baseImagePath.concat(currentImagePath)).into(imageView);
        }
    }
}