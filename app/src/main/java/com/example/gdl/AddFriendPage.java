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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

public class AddFriendPage extends GDLActivity {

    private static final String TAG = "AddFriendPage";

    Button searchButton;
    Button addButton;
    EditText editTextFriendID;
    TextView personalID;
    TextView friendName;
    ImageView friendPFP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_friend);

        searchButton = findViewById(R.id.searchBtn);
        addButton = findViewById(R.id.addFriendBtn);
        editTextFriendID = findViewById(R.id.editTextFriendID);
        personalID = findViewById(R.id.personalIDNumber);
        friendName = findViewById(R.id.friendName);
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
                //TODO: query database using the editText text, get user info
                //Query getMember = mRootDatabaseRef.child("Users").equalTo(editTextFriendID.getText().toString());
            }
        });
    }


}
