package com.apps.shreya.chatapplication.topics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.shreya.chatapplication.R;

public class ArtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);

        android.support.v7.widget.Toolbar topic1 = findViewById(R.id.topictoolbar);
        setSupportActionBar(topic1);


        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
