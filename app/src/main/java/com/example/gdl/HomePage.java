package com.example.gdl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatButton;

import com.example.gdl.Glide.GlideApp;
import com.example.gdl.createeventpg.CreateEventMain;
import com.example.gdl.eventlistpg.EventListActivity;
import com.example.gdl.models.Bill;
import com.example.gdl.models.Event;
import com.example.gdl.models.Member;
import com.example.gdl.myfriendspg.FriendListPage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

public class HomePage extends GDLActivity {

    private static final String TAG = "HomePage";
    
    AppCompatButton joinEvent;
    AppCompatButton createEvent;
    TextView myEvents;
    TextView myFriends;
    TextView pendingPayments;
    TextView addFriends;

    TextView mUsername;
    TextView mUserEmail;
    ImageView mProfilePic;
    TextView mNoEventsOngoing;
    TextView mPendingPaymentsAmt;
    TextView mAmtToReceive;

    Member currentUser = null;
    StorageReference userImageStorageRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        Log.d(TAG, "onCreate: called");

        //set actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        //get stuff from authentication database to display
        mUsername = findViewById(R.id.username);
        mUserEmail = findViewById(R.id.user_email);
        mProfilePic = findViewById(R.id.profile_pic);
        mNoEventsOngoing = findViewById(R.id.no_of_events_ongoing);
        mPendingPaymentsAmt = findViewById(R.id.pending_payments_amt);
        mAmtToReceive = findViewById(R.id.amt_to_receive);
        Log.d(TAG, "onCreate: getting profile pic from storage");
        StorageReference imageStorageRef = storageRef.child("Profile Images");
        Log.d(TAG, "onCreate: uid: "+user.getUid());
        userImageStorageRef = imageStorageRef.child(user.getUid() + ".jpg");
        try {
            GlideApp.with(this)
                    .load(userImageStorageRef)
                    .into(mProfilePic);
            Log.d(TAG, "onCreate: profile pic obtained");
        } catch (Exception e) {
            Log.d(TAG, "onCreate: cannot set photo");
        }

        //mUserEmail.setText(user.getEmail());
        Log.d(TAG, "onCreate: getting user details from firestore");
        DocumentReference userRef = db.collection("Users").document(user.getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(Member.class);
                Log.d(TAG, "onSuccess: obtained current user details from firestore");
                Log.d(TAG, "onCreate: setting current user details to views");
                String lent = ""+currentUser.getLent();
                String numEventsOngoing = "0";
                if (currentUser.getEventsList() != null) {
                    numEventsOngoing = ""+currentUser.getEventsList().size();
                }
                String debt = ""+currentUser.getDebt();
                String name = ""+currentUser.getName();
                String email = ""+currentUser.getEmail();
                mUserEmail.setText(email);
                mAmtToReceive.setText(lent);
                mNoEventsOngoing.setText(numEventsOngoing);
                mPendingPaymentsAmt.setText(debt);
                mUsername.setText(name);
            }
        });

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
                Toast pendingPaymentsPg = Toast.makeText(HomePage.this, "Go to pending payments", Toast.LENGTH_SHORT);
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

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: called");

    }


}
