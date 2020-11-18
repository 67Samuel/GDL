package com.example.gdl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {
    public static final String ACTIVITY_TAG = "MainActivity";
    Button home_button;
    Button events_button;
    Button friends_button;
    Button add_friends_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        home_button = findViewById(R.id.home_button);
        events_button = findViewById(R.id.events_button);
        friends_button = findViewById(R.id.friends_button);
        add_friends_button = findViewById(R.id.add_friend_button);

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_TAG, "Home button pressed");
            }
        });

        events_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_TAG, "Events button pressed");
            }
        });

        friends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_TAG, "Friends button pressed");
            }
        });

        add_friends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_TAG, "AF button pressed");
            }
        });
    }
}
