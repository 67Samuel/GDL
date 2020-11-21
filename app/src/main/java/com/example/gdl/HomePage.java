package com.example.gdl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import static android.widget.Toast.*;

public class HomePage extends ActivityWithMenu {
    public static final String ACTIVITY_TAG = "MainActivity";
    AppCompatButton joinEvent;
    AppCompatButton createEvent;
    TextView myEvents;
    TextView myFriends;
    TextView pendingPayments;
    TextView addFriends;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //set actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        myEvents = findViewById(R.id.my_events_button);
        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast joinEventsPg = Toast.makeText(HomePage.this, "Go to Join Events", Toast.LENGTH_SHORT);
                joinEventsPg.show();
            }
        });

        myFriends = findViewById(R.id.my_friends_button);
        myFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast myFriendsPg = Toast.makeText(HomePage.this, "Go to My Friends", Toast.LENGTH_SHORT);
                myFriendsPg.show();
            }
        });

        pendingPayments = findViewById(R.id.pending_payments_button);
        pendingPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast pendingPaymentsPg = Toast.makeText(HomePage.this, "Go to pendingpayments", Toast.LENGTH_SHORT);
                pendingPaymentsPg.show();
            }
        });

        addFriends = findViewById(R.id.add_friend_button);
        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast addFriendsPg = Toast.makeText(HomePage.this, "Go to add Friends", Toast.LENGTH_SHORT);
                addFriendsPg.show();
            }
        });

        joinEvent = findViewById(R.id.join_event_button);
        joinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast joinEventPg = Toast.makeText(HomePage.this, "Go to Join Events", Toast.LENGTH_SHORT);
                joinEventPg.show();
            }
        });

        createEvent = findViewById(R.id.create_event_button);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast joinEventPg = Toast.makeText(HomePage.this, "Go to createEvent", Toast.LENGTH_SHORT);
                joinEventPg.show();
            }
        });


    }

}
