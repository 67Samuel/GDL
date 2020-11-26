package com.example.gdl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.gdl.eventlistpg.EventListActivity;
import com.example.gdl.myfriendspg.FriendListPage;

public class ActivityWithMenu extends AppCompatActivity {
    public static final String TAG = "ActivityWithMenu";

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home: //title of item is it's id
                Toast.makeText(this, "Go to Home", Toast.LENGTH_SHORT).show();
                //go to home activity
                Intent homeIntent = new Intent(this, HomePage.class);
                startActivity(homeIntent);
                return true;
            case R.id.menu_my_events:
                Toast.makeText(this, "Go to My Events", Toast.LENGTH_SHORT).show();
                Intent myEventsIntent = new Intent(this, EventListActivity.class);
                startActivity(myEventsIntent);
                //go to my_events activity
                return true;
            case R.id.menu_my_friends:
                Toast.makeText(this, "Go to My Friends", Toast.LENGTH_SHORT).show();
                //go to my_friends activity
                Intent myFriendsIntent = new Intent(this, FriendListPage.class);
                startActivity(myFriendsIntent);
                return true;
            case R.id.menu_add_friend:
                Toast.makeText(this, "Go to Add Friends", Toast.LENGTH_SHORT).show();
                //save instance state
                //go to add_friends activity
                Intent addFriendsIntent = new Intent(this, AddFriendPage.class);
                startActivity(addFriendsIntent);
                return true;

            case R.id.menu_log_out:
                //Context mContext = getApplicationContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityWithMenu.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Log Out");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor loginPreferencesEditor = getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE).edit();
                                loginPreferencesEditor.clear();
                                loginPreferencesEditor.apply();
                                Intent intent = new Intent(ActivityWithMenu.this, HomePage.class);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
