     package com.example.gdl;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFriendPage extends GDLActivity {

    private static final String TAG = "AddFriendPage";

    Button searchButton;
    Button addButton;
    EditText editTextFriendID;
    TextView personalID;
    TextView friendNameTextView;
    ImageView friendPFP;
    Boolean search_Successful = false;
    String current_Friend_ID;
    String current_Friend_Name;
    ArrayList friend_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_friend);

        searchButton = findViewById(R.id.searchBtn);
        addButton = findViewById(R.id.addFriendBtn);
        editTextFriendID = findViewById(R.id.editTextFriendID);
        personalID = findViewById(R.id.personalIDNumber);
        friendNameTextView = findViewById(R.id.friendName);
        friendPFP = findViewById(R.id.friendPFP);

        FirebaseUser user = mAuth.getCurrentUser();
        personalID.setText(user.getUid());

        personalID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Your ID", personalID.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(AddFriendPage.this, "ID copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("Users").document(editTextFriendID.getText().toString()); //change id to friend's id (editTextFriendID)
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                current_Friend_ID = editTextFriendID.getText().toString();
                                current_Friend_Name = document.getData().get("name").toString(); //change key to whatever the name is
                                friendNameTextView.setText(current_Friend_Name);
                                search_Successful = true;
                            } else {
                                //Toast.makeText(AddFriendPage.this, "Friend not found", Toast.LENGTH_LONG).show();
                                friendNameTextView.setText("Friend not found");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_Successful = true) {

                    //get the current friend's list, add this one if it's not inside
                    DocumentReference docRef = db.collection("Users").document(user.getUid()); //change id to friend's id (editTextFriendID)
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                        friend_list = (ArrayList) document.getData().get("friendsList"); // get friendlist
                                        if (friend_list.contains(current_Friend_ID) == false) { // if searched user not in list
                                            friend_list.add(current_Friend_ID); //add to list
                                            Map<String, Object> friendToAdd = new HashMap<>();
                                            friendToAdd.put("friendsList", friend_list);
                                            db.collection("Users").document(user.getUid()).set(friendToAdd, SetOptions.merge()); //update list
                                            Toast.makeText(AddFriendPage.this, "Friend added", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(AddFriendPage.this, "Already a friend", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                    //Toast.makeText(AddFriendPage.this, "Friend not found", Toast.LENGTH_LONG).show();
                                    friendNameTextView.setText("Friend not found");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddFriendPage.this, "Search for a friend first", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
