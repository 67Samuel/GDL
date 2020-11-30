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

import com.example.gdl.createeventpg.CreateEventMain;
import com.example.gdl.eventlistpg.EventListActivity;
import com.example.gdl.myfriendspg.FriendListPage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomePage extends GDLActivity {
    public static final String ACTIVITY_TAG = "MainActivity";
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

    String Uid;

    final StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);


        //set actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        //get stuff from authentication database to display
        mUsername = findViewById(R.id.username);
        mUserEmail = findViewById(R.id.user_email);
        mProfilePic = findViewById(R.id.profile_pic);
        FirebaseUser user = mAuth.getCurrentUser();
        mUsername.setText((String)user.getDisplayName());
        mUserEmail.setText((String)user.getEmail());
        if (user.getPhotoUrl() != null) {
            Toast.makeText(this, "pic uri: "+user.getPhotoUrl(), Toast.LENGTH_SHORT).show();
            try {
                Task<ListResult> taskListResult = storageRef.child("background").listAll();
                taskListResult.continueWithTask(new Continuation<ListResult, Task<byte[]>>() {
                                                    @Override
                                                    public Task<byte[]> then(@NonNull Task<ListResult> task) throws Exception {
                                                        ListResult listResult = task.getResult();
                                                        ArrayList<StorageReference> refs = new ArrayList<>(listResult.getItems());
                                                        StorageReference ref = refs.get(0);
                                                        return FireBaseUtils.downloadToImageView(HomePage.this, ref, mProfilePic);
                                                    }
                });
            } catch (Exception e) {
                Log.d(TAG, "pic creation error: "+e);
            }
        }

        //get stuff from realtime database to display
        mNoEventsOngoing = findViewById(R.id.no_of_events_ongoing);
        mPendingPaymentsAmt = findViewById(R.id.pending_payments_amt);
        mAmtToReceive = findViewById(R.id.amt_to_receive);

        Uid = user.getUid();
        //TODO: implement listener for debt
        DatabaseReference userDebtRef = mRootDatabaseRef.child("Users/"+Uid+"/debt");
        userDebtRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if (TextUtils.isEmpty(value)) {
                    mPendingPaymentsAmt.setText("0");
                } else {
                    mPendingPaymentsAmt.setText(value);
                    if (value.equals("0")) {
                        mPendingPaymentsAmt.setTextColor(Color.GREEN);
                    } else {
                        mPendingPaymentsAmt.setTextColor(Color.RED);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "pending payment retrieval error", Toast.LENGTH_SHORT).show();
            }
        });
        //TODO: implement listener for to receive
        DatabaseReference userLentRef = mRootDatabaseRef.child("Users/"+Uid+"/lent");
        userLentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if (TextUtils.isEmpty(value)) {
                    mAmtToReceive.setText("0");
                } else {
                    mAmtToReceive.setText(value);
                    if (value == "0") {
                        mAmtToReceive.setTextColor(Color.WHITE);
                    } else {
                        mAmtToReceive.setTextColor(Color.GREEN);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "amt to receive retrieval error", Toast.LENGTH_SHORT).show();
            }
        });
        //TODO: implement listener for num events ongoing
        DatabaseReference userNumEventsRef = mRootDatabaseRef.child("Users/"+Uid+"/events");
        userNumEventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mNoEventsOngoing.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "num events ongoing retrieval error", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: called");

    }


}
