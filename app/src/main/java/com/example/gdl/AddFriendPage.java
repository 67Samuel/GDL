package com.example.gdl;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddFriendPage extends GDLActivity {

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
    }


}
