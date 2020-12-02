package com.example.gdl.createeventpg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gdl.GDLActivity;
import com.example.gdl.R;
import com.example.gdl.models.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CreateEventSelectMembers extends GDLActivity implements RecyclerItemSelectedListener {

    private static final String TAG = "CreateEventMembers";

    //UI components
    private RecyclerView mMemberRecyclerView;
    private MembersAdapter mMembersAdapter;
    private RecyclerView.LayoutManager mMemberLayoutManager;
    private androidx.appcompat.widget.SearchView mSearchView;
    private Button mSelectMembersDoneButton;
    private ChipGroup mSelectedMembersChipGroup;

    //vars
    SharedPreferences mPreferences;
    private static final String sharedPrefFile = "com.example.gdl.createeventpg.CreateEventSelectMembers.preffile";
    public final static String SELECTED_MEMBERS_ID_KEY = "com.example.gdl.CreateEventActivities.selected_members_ids_key";
    public ArrayList<String> mSelectedMembersIds = new ArrayList<>();
    public ArrayList<Member> friendsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.create_event_select_members);
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

        //get shared pref
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Set<String> membersIdsSet = this.mPreferences.getStringSet(SELECTED_MEMBERS_ID_KEY, null);
        if (membersIdsSet != null && !membersIdsSet.isEmpty()) {
            Log.d(TAG, "onCreate: getting shared pref");
            mSelectedMembersIds.clear();
            Log.d(TAG, "onCreate: clearing mSelectedMembersIds");
            for (String s : membersIdsSet) {
                onItemSelected(s);
            }
        }

        //TODO: get all friends of user (Member objs) and store in friendsList, find out if friends is a collection or subcollection
        DocumentReference userRef = db.collection("Users").document(user.getUid()); //ref to list of friends
        CollectionReference friendsRef = userRef.collection("friends");
        friendsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Member friend = document.toObject(Member.class);
                        friendsList.add(friend);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        mMemberLayoutManager = new LinearLayoutManager(this);
        mMemberRecyclerView.setLayoutManager(mMemberLayoutManager);
        mMembersAdapter = new MembersAdapter(this, friendsList);
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
                if (mSelectedMembersIds.isEmpty()) {
                    Toast.makeText(CreateEventSelectMembers.this, "Select at least one member", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    intent.putExtra(SELECTED_MEMBERS_ID_KEY, mSelectedMembersIds);
                }
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(String id) {
        if (!mSelectedMembersIds.contains(id)) {
            Log.d(TAG, "onItemSelected: making chip");
            Chip chip = new Chip(this);
            //TODO: get member name from database using id
            chip.setText("id: "+id);
            ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
            chip.setChipDrawable(drawable);
            chip.setCloseIconVisible(true);
            chip.setCheckable(false);
            chip.setClickable(false);
            chip.setChipIconResource(R.drawable.ashketchum);
            //TODO: set icon with picture from member after member can get pic from database
            //chip.setChipIcon(ContextCompat.getDrawable(this, member.getPicId()));
            chip.setPadding(80, 10, 80, 10);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectedMembersChipGroup.removeView(chip);
                    mSelectedMembersIds.remove(id);
                }
            });
            mSelectedMembersChipGroup.addView(chip);
            mSelectedMembersIds.add(id);
            mSelectedMembersChipGroup.setVisibility(View.VISIBLE);
        }
    }

    private void saveSharedPreferences() {
        //saving mSelectedMembersIds as a String set to saved pref
        if (mPreferences != null) {
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            int count = mSelectedMembersIds.size();
            if (count > 0) {
                Set<String> selectedMembersIdsSet = new HashSet<String>();
                for (String i : mSelectedMembersIds) {
                    selectedMembersIdsSet.add(String.valueOf(i));
                }
                preferencesEditor.putStringSet(SELECTED_MEMBERS_ID_KEY, selectedMembersIdsSet);
            } else {
                preferencesEditor.remove(SELECTED_MEMBERS_ID_KEY);
            }
            preferencesEditor.apply();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //save an integer array of selected members ids
        saveSharedPreferences();
    }
}