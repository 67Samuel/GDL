package com.example.gdl;

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

import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinEventActivity extends GDLActivity {

    private static final String TAG = "JoinEventActivity";

    Button join_event_search_button;
    Button join_event_join_button;
    EditText join_event_edit_text;
    TextView event_name;
    TextView event_owner;
    ImageView event_picture;
    Boolean search_Successful = false;
    String current_event_id;
    List event_list;

;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_event);

        join_event_search_button = findViewById(R.id.join_event_search_button);
        join_event_join_button = findViewById(R.id.join_event_search_button2);
        join_event_edit_text = findViewById(R.id.join_event_eventid_edittext);
        event_name = findViewById(R.id.join_event_name);
        event_picture = findViewById(R.id.join_event_image);

        FirebaseUser user = mAuth.getCurrentUser();

        join_event_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("Events").document(join_event_edit_text.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                current_event_id = join_event_edit_text.getText().toString();
                                event_name.setText(document.getData().get("name").toString());
                                //event_picture.setImageURI((Uri) document.getData().get("eventPicture"));
                                search_Successful = true;
                            } else {
                                event_name.setText("Event not found");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });


        join_event_join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_Successful) {
                    DocumentReference docRef = db.collection("Users").document(user.getUid());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    event_list = (ArrayList) document.getData().get("eventsList"); //get existing eventslist
                                    if (event_list.contains(current_event_id) == false) { //if event not in eventlist
                                        event_list.add(current_event_id); //add to eventlist
                                        Map<String, Object> eventToAdd = new HashMap<>();
                                        eventToAdd.put("eventsList", event_list);
                                        db.collection("Users").document(user.getUid()).set(eventToAdd, SetOptions.merge()); //update list
                                        Toast.makeText(JoinEventActivity.this, "Event added", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(JoinEventActivity.this, "Already in event", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    event_name.setText("Event not found");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                } else {
                    Toast.makeText(JoinEventActivity.this, "Search for event first", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}