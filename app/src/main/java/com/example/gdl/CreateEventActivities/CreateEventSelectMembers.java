package com.example.gdl.CreateEventActivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gdl.ActivityWithMenu;
import com.example.gdl.R;
import com.example.gdl.adapters.MembersAdapter;
import com.example.gdl.interfaces.RecyclerItemSelectedListener;
import com.example.gdl.models.Member;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class CreateEventSelectMembers extends ActivityWithMenu implements RecyclerItemSelectedListener {

    //TODO: make shared preferences to store previously created ArrayList so that user does not have to re-enter members just to add/remove one

    private static final String TAG = "CreateEventMembers";

    //UI components
    private RecyclerView mMemberRecyclerView;
    private MembersAdapter mMembersAdapter;
    private RecyclerView.LayoutManager mMemberLayoutManager;
    private androidx.appcompat.widget.SearchView mSearchView;
    private Button mSelectMembersDoneButton;
    private ChipGroup mSelectedMembersChipGroup;

    //vars
    public ArrayList<Member> mFakeMembers = new ArrayList<>();
    public final static String SELECTED_MEMBERS_KEY = "com.example.gdl.CreateEventActivities.selected_members_key";
    private ArrayList<Member> mSelectedList = new ArrayList<>(); //members selected to be in event

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.activity_create_event_select_members);
        Log.d(TAG, "onCreate: layout set up");

        //set actionbar
        ActionBar actionBar = getSupportActionBar();
        Log.d(TAG, "onCreate: action bar created");
        assert actionBar != null;
        actionBar.setTitle("Create Event    ");
        actionBar.setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onCreate: action bar fin");

        //find views
        mMemberRecyclerView = findViewById(R.id.select_members_recycler_view);
        mSelectMembersDoneButton = findViewById(R.id.select_members_done_button);
        mSelectedMembersChipGroup = findViewById(R.id.selected_members_chip_group);
        Log.d(TAG, "onCreate: views found");

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
                if (mSelectedList.isEmpty()) {
                    Toast.makeText(CreateEventSelectMembers.this, "Select at least one member", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    intent.putExtra(SELECTED_MEMBERS_KEY, mSelectedList);
                }
                startActivity(intent);
            }
        });

    }

    public void getFakeMembers() {
        Member.setIds(20);
        Log.d(TAG, "getFakeMembers: fake ids set up");
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

    @Override
    public void onItemSelected(Member member) {
        if (!mSelectedList.contains(member)) {
            Chip chip = new Chip(this);
            chip.setText(member.getName());
            //Line below is probably a better way to get images when pic comes from database
            //chip.setChipIcon(ContextCompat.getDrawable(this, member.getPicId()));
            ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
            chip.setChipDrawable(drawable);
            chip.setCloseIconVisible(true);
            chip.setCheckable(false);
            chip.setClickable(false);
            chip.setChipIconResource(R.drawable.ashketchum);
            chip.setPadding(80, 10, 80, 10);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectedMembersChipGroup.removeView(chip);
                    mSelectedList.remove(member);
                }
            });
            mSelectedMembersChipGroup.addView(chip);
            mSelectedList.add(member);
            mSelectedMembersChipGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mFakeMembers.clear();
        Log.d(TAG, "onStop: called");
    }
}