package com.example.gdl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatButton;

import com.example.gdl.createeventpg.CreateEventMain;
import com.example.gdl.eventlistpg.EventListActivity;
import com.example.gdl.myfriendspg.FriendListPage;

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
                Toast myEventsPg = Toast.makeText(HomePage.this, "Go to My Events", Toast.LENGTH_SHORT);
                myEventsPg.show();
                Intent intent = new Intent(HomePage.this, EventListActivity.class);
                startActivity(intent);
            }
        });

        myFriends = findViewById(R.id.my_friends_button);
        myFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, FriendListPage.class);
                startActivity(intent);
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
                Intent intent = new Intent(HomePage.this, AddFriendPage.class);
                startActivity(intent);
            }
        });

        joinEvent = findViewById(R.id.join_event_button);
        joinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast joinEventPg = Toast.makeText(HomePage.this, "Go to Join Events", Toast.LENGTH_SHORT);
                joinEventPg.show();
                Intent intent = new Intent(HomePage.this, JoinEventActivity.class);
                startActivity(intent);
            }
        });

        createEvent = findViewById(R.id.create_event_button);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast joinEventPg = Toast.makeText(HomePage.this, "Go to createEvent", Toast.LENGTH_SHORT);
                joinEventPg.show();
                Intent intent = new Intent(HomePage.this, CreateEventMain.class);
                startActivity(intent);
            }
        });


    }

}
