package com.example.gdl.addbillspg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gdl.R;

import java.util.ArrayList;

public class AddBillActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    MemberListAdapter mAdapter;

    // TODO 3.0 Remove the memberArr dummy data
    ArrayList<String> mMemberList = new ArrayList<>();
    String[] memberArr = {"Member 1", "Member 2", "Member 3", "Member 4", "Member 5", "Member 6", "Member 7", "Member 8", "Member 9", "Member 10"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bill_page_top_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recyclerview);
       		
		
		// TODO 3.1.1 Replace the for loop below with an initialization of a Bill Object
        // TODO 3.1.2 Add this Bill Object to the ArrayList of Bill Object of the Event Object
        // TODO 3.2 Check that the Bill Object has an ArrayList each to hold the Payer and Payee name
        // TODO 3.3 Retrieve the ArrayList of Member name from the Event Object to be displayed in the Recycler View
        for(String member: memberArr) {
            mMemberList.add(member);
        }
		
		mAdapter = new MemberListAdapter(this, mMemberList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_bill_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.buttonSaveBill) {
            // TODO 4.3 Call finish() to go back to the originating Activity
        }
        return super.onOptionsItemSelected(item);
    }
}