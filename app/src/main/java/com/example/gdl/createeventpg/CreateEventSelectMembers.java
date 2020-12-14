package com.example.gdl.createeventpg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.gdl.AddFriendPage;
import com.example.gdl.GDLActivity;
import com.example.gdl.Glide.GlideApp;
import com.example.gdl.R;
import com.example.gdl.Utils;
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
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    //SharedPreferences mPreferences;
    //private static final String sharedPrefFile = "com.example.gdl.createeventpg.CreateEventSelectMembers.preffile";
    //public final static String SELECTED_MEMBERS_ID_KEY = "com.example.gdl.CreateEventActivities.selected_members_ids_key";
    public ArrayList<String> mSelectedMembersIds = new ArrayList<>();
    public List<Member> friendsList = new ArrayList<>();
    public List<String> friendsIdList = new ArrayList<>();
    int counter = 0;
    String friendsName;
    StorageReference friendImageStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.create_event_select_members);
        Log.d(TAG, "onCreate: layout set up");

        Utils utils = new Utils();

        CreateEventSelectMembersSharedPref.createSharedPref(getApplicationContext());

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
        //mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Set<String> membersIdsSet = this.mPreferences.getStringSet(SELECTED_MEMBERS_ID_KEY, null);
        Set<String> membersIdsSet = CreateEventSelectMembersSharedPref.readStringSet(CreateEventSelectMembersSharedPref.SELECTED_MEMBERS_ID_SET_KEY, null);
        if (membersIdsSet != null && !membersIdsSet.isEmpty()) {
            Log.d(TAG, "onCreate: getting shared pref");
            mSelectedMembersIds.clear();
            Log.d(TAG, "onCreate: clearing mSelectedMembersIds");
            for (String s : membersIdsSet) {
                onItemSelected(s);
            }
        }

        //get all user's friends as Member classes
        DocumentReference userRef = db.collection("Users").document(user.getUid());
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot friendsDocument = task.getResult();
                    if (friendsDocument.exists()) {
                        friendsIdList = (ArrayList) friendsDocument.getData().get("friendsList");
                        Log.d(TAG, "onComplete: got friends id list from db");
                        CollectionReference usersCollection = db.collection("Users");
                        counter = friendsIdList.size();
                        for (String id : friendsIdList) {
                            DocumentReference friendRef = usersCollection.document(id);
                            friendRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    counter--;
                                    friendsList.add(documentSnapshot.toObject(Member.class));
                                    if (counter == 0) {
                                        Member friend = friendsList.get(0);
                                        Log.d(TAG, "onComplete: friends: " + friend.toString());
                                        mMemberLayoutManager = new LinearLayoutManager(CreateEventSelectMembers.this);
                                        mMemberRecyclerView.setLayoutManager(mMemberLayoutManager);
                                        mMembersAdapter = new MembersAdapter(CreateEventSelectMembers.this, friendsList);
                                        mMemberRecyclerView.setAdapter(mMembersAdapter);
                                        Log.d(TAG, "onCreate: recyclerview set up");

                                        mSearchView = findViewById(R.id.select_members_search_bar);
                                        setListeners();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
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
                Log.d(TAG, "onClick: going to create event main");
                Intent intent = new Intent(CreateEventSelectMembers.this, CreateEventMain.class);
                if (mSelectedMembersIds.isEmpty()) {
                    Toast.makeText(CreateEventSelectMembers.this, "Select at least one member", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    intent.putStringArrayListExtra(CreateEventSelectMembersSharedPref.SELECTED_MEMBERS_ID_SET_KEY,  mSelectedMembersIds);
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
            db.collection("Users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            friendsName = document.getData().get("name").toString();
                            chip.setText(friendsName);
                            ChipDrawable drawable = ChipDrawable.createFromAttributes(CreateEventSelectMembers.this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
                            chip.setChipDrawable(drawable);
                            chip.setCloseIconVisible(true);
                            chip.setCheckable(false);
                            chip.setClickable(false);
                            chip.setChipBackgroundColorResource(R.color.translucentGreen);
                            chip.setPadding(50, 10, 80, 10);
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
                }
            });
        }
    }

    /*private void saveSharedPreferences() {
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
     */

    private void saveSharedPreferences() {
        int count = mSelectedMembersIds.size();
        if (count > 0) {
            Set<String> selectedMembersIdsSet = new HashSet<String>();
            for (String i : mSelectedMembersIds) {
                selectedMembersIdsSet.add(String.valueOf(i));
            }
            CreateEventSelectMembersSharedPref.write(CreateEventSelectMembersSharedPref.SELECTED_MEMBERS_ID_SET_KEY, selectedMembersIdsSet);
        } else {
            CreateEventSelectMembersSharedPref.remove(CreateEventSelectMembersSharedPref.SELECTED_MEMBERS_ID_SET_KEY);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //save an integer array of selected members ids
        saveSharedPreferences();
    }
}