package com.example.andriod.tmdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    final static String INTENT_KEY = "Index";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textView = findViewById(R.id.textView);

        Intent intent = getIntent();

        if(intent.hasExtra(INTENT_KEY)){
            int index = intent.getIntExtra(INTENT_KEY,-1);
            textView.setText(String.valueOf(index));
        }
    }
}