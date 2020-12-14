package com.example.gdl.myfriendspg;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdl.GDLActivity;
import com.example.gdl.R;
import com.example.gdl.createeventpg.CreateEventSelectMembers;
import com.example.gdl.createeventpg.MembersAdapter;
import com.example.gdl.models.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FriendListPage extends GDLActivity {

    private static final String TAG = "FriendListPage";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayourManager;

    List<Member> friendsList = new ArrayList<>();
    List<String> friendsIdList = new ArrayList<>();
    int counter = 0;
    int current_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

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
                                        mRecyclerView = findViewById(R.id.recycler_view);
                                        mRecyclerView.setHasFixedSize(true);
                                        mLayourManager = new LinearLayoutManager(FriendListPage.this);
                                        mAdapter = new RecyclerViewAdapter(FriendListPage.this, friendsList);
                                        mRecyclerView.setLayoutManager(mLayourManager);
                                        mRecyclerView.setAdapter(mAdapter);
                                        Log.d(TAG, "onCreate: recyclerview set up");
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}
