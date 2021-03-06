     package com.example.gdl;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
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
    List friend_list;
    List friends_friend_list;
    Uri current_Friend_Pic_Uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_friend);

        Utils utils = new Utils();

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
                                current_Friend_Name = document.getData().get("name").toString();
                                try {
                                    current_Friend_Pic_Uri = Uri.parse(document.getData().get("picId").toString());
                                    Glide.with(AddFriendPage.this)
                                            .load(current_Friend_Pic_Uri)
                                            .into(friendPFP);
                                } catch (Exception e) {
                                    Log.d(TAG, "onComplete: "+e);
                                    Log.d(TAG, "onComplete: friend has not set profile pic");
                                    friendPFP.setImageResource(R.drawable.ashketchum);
                                }
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
                if (search_Successful) {

                    //get the current user's list, add this one if it's not inside
                    DocumentReference docRef = db.collection("Users").document(user.getUid());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                        friend_list = (ArrayList) document.getData().get("friendsList"); // get friendlist
                                        if (friend_list.contains(current_Friend_ID) == false) { // if searched user not alr a friend
                                            friend_list.add(current_Friend_ID); //add to list
                                            Map<String, Object> friendToAdd = new HashMap<>();
                                            friendToAdd.put("friendsList", friend_list);
                                            db.collection("Users").document(user.getUid()).set(friendToAdd, SetOptions.merge()); //update list
                                            Toast.makeText(AddFriendPage.this, "Friend added", Toast.LENGTH_LONG).show();

                                            //set user as new friend's friend
                                            DocumentReference friendRef = db.collection("Users").document(current_Friend_ID);
                                            friendRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot friendsDocument = task.getResult();
                                                        if (friendsDocument.exists()) {
                                                            friends_friend_list = (ArrayList) friendsDocument.getData().get("friendsList");
                                                            if (friends_friend_list.contains(user.getUid()) == false) {
                                                                friends_friend_list.add(user.getUid());
                                                                Map<String, Object> userToAdd = new HashMap<>();
                                                                userToAdd.put("friendsList", friends_friend_list);
                                                                db.collection("Users").document(current_Friend_ID).set(userToAdd, SetOptions.merge());
                                                                Log.d(TAG, "onComplete: friend added user");
                                                            }
                                                        }
                                                    }
                                                }
                                            });

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
