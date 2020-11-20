package com.example.gdl;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gdl.CreateEventActivities.CreateEventMain;

public class ActivityWithMenu extends AppCompatActivity {

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
                Intent intent = new Intent(this, CreateEventMain.class);
                startActivity(intent);
                //go to my_events activity
                return true;
            case R.id.menu_my_friends:
                Toast.makeText(this, "Go to My Friends", Toast.LENGTH_SHORT).show();
                //go to my_friends activity
                return true;
            case R.id.menu_add_friend:
                Toast.makeText(this, "Go to Add Friends", Toast.LENGTH_SHORT).show();
                //save instance state
                //go to add_friends activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
