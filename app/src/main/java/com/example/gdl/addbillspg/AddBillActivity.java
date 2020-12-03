package com.example.gdl.addbillspg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gdl.R;
import com.example.gdl.models.Member;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddBillActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    MemberListAdapter mAdapter;

    // TODO 3.0 Remove the memberArr dummy data
    ArrayList<String> mMemberList = new ArrayList<>();
    String[] memberArr = {"Member 1", "Member 2", "Member 3", "Member 4", "Member 5", "Member 6", "Member 7", "Member 8", "Member 9", "Member 10"};
    ArrayList<Member> eventMember = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bill_page_top_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recyclerview);

        // TODO Get the ArrayList of Member Object from Event Object instead of adding manually
        eventMember.add(new Member("Member 1", "001"));
        eventMember.add(new Member("Member 2", "002"));
        eventMember.add(new Member("Member 3", "003"));
        eventMember.add(new Member("Member 4", "004"));
        eventMember.add(new Member("Member 5", "005"));
        eventMember.add(new Member("Member 6", "006"));
        eventMember.add(new Member("Member 7", "007"));
        eventMember.add(new Member("Member 8", "008"));

		
		mAdapter = new MemberListAdapter(this, eventMember);
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
            // Dummy Data
            Map<String, Object> billData = new HashMap<>();
            billData.put("name", "Bill2");
            billData.put("amount", 100);
            billData.put("memberSize", 2);
            billData.put("memberIDLists", mAdapter.memberViewHolder.payeeID);
            billData.put("payerID", mAdapter.memberViewHolder.payerID);
            db.collection("Bills").add(billData);

            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}