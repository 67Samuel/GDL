package com.example.gdl.CreateEventActivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gdl.R;
import com.example.gdl.adapters.MembersAdapter;
import com.example.gdl.models.Member;

import java.util.ArrayList;

public class CreateEventSelectMembers extends AppCompatActivity {

    private static final String TAG = "CreateEventMembers";

    //UI components
    private RecyclerView mMemberRecyclerView;
    private MembersAdapter mMembersAdapter;
    private RecyclerView.LayoutManager mMemberLayoutManager;
    private androidx.appcompat.widget.SearchView mSearchView;
    private Button mSelectMembersDoneButton;

    //vars
    public ArrayList<Member> mFakeMembers = new ArrayList<>();
    public final static String SELECTED_MEMBERS_KEY = "com.example.gdl.CreateEventActivities.selected_members_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("creating CreateEventMembers activity");
        setContentView(R.layout.activity_create_event_select_members);

        //set actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Create Event");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //find views
        mMemberRecyclerView = findViewById(R.id.select_members_recycler_view);
        mSelectMembersDoneButton = findViewById(R.id.select_members_done_button);

        getFakeMembers();
        Log.d(TAG, "onCreate: fake members made");

        mMemberLayoutManager = new LinearLayoutManager(this);
        mMemberRecyclerView.setLayoutManager(mMemberLayoutManager);
        mMembersAdapter = new MembersAdapter(this, mFakeMembers);
        mMemberRecyclerView.setAdapter(mMembersAdapter);
        Log.d(TAG, "onCreate: recyclerview set up");

        mSearchView = findViewById(R.id.select_members_search_bar);
        setListeners();

    }

    private void setListeners() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMembersAdapter.getFilter().filter(newText);
                return false;
            }
        });
        mSelectMembersDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEventSelectMembers.this, CreateEventMain.class);
                if (mMembersAdapter.getSelectedList().isEmpty()) {
                    Toast.makeText(CreateEventSelectMembers.this, "Select at least one member", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    intent.putExtra(SELECTED_MEMBERS_KEY, mMembersAdapter.getSelectedList());
                }
                startActivity(intent);
            }
        });

    }

    public void getFakeMembers() {
        Member.setIds(20);
        mFakeMembers.add(new Member("sally"));
        mFakeMembers.add(new Member("james"));
        mFakeMembers.add(new Member("dillon"));
        mFakeMembers.add(new Member("rebecca"));
        mFakeMembers.add(new Member("lucy"));
        mFakeMembers.add(new Member("isaac"));
        mFakeMembers.add(new Member("joshua"));
        mFakeMembers.add(new Member("kelly"));
        mFakeMembers.add(new Member("percy"));
    }

}