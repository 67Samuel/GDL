package com.example.gdl.eventlistpg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.gdl.eventdetailspg.EventActivity;
import com.example.gdl.models.Event;
import com.example.gdl.R;
import com.example.gdl.models.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class EventListFragment extends Fragment {
    public static final String TAG = "EventListFragment";
    //save event data
    private List<Event> eventList = new ArrayList<>();
    private List<String> eventIDs;
    private ListView event_listview;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseUser user = mAuth.getCurrentUser();
    public DocumentReference docRef;

    final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    interface FScallback {
        void callback(List<String> eventsIds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_listview, container, false);
        final EventAdapter[] adapter = {new EventAdapter(getContext(), R.layout.event_item, eventList)};
        event_listview = view.findViewById(R.id.event_listview);

        // get the date and use it to create adapter
        user = mAuth.getCurrentUser();


        docRef = db.collection("Users").document(user.getUid());
        Log.d(TAG, "USER-EMAIL: " + user.getUid());
        //DB Test - Query for the user
        db.collection("Users")
                .whereEqualTo(FieldPath.documentId(), user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        QuerySnapshot querySnap = task.getResult();
                        List<DocumentSnapshot> docSnapList = querySnap.getDocuments();
                        DocumentSnapshot docSnap = docSnapList.get(0);
                        Map<String, Object> s = docSnap.getData();
                        List<String> eventsIds = (List<String>) s.get("eventsList");
                        db.collection("Events")
                                .whereIn("id", eventsIds)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                eventList.add(document.toObject(Event.class));
                                            }
                                            adapter[0] = new EventAdapter(getContext(), R.layout.event_item, eventList);
                                        }
                                    }
                                });

                        Log.d(TAG, "Task successful");
                        Log.d(TAG, eventsIds.toString());
                        Log.d(TAG, docSnap.getId());
                    } catch (IllegalArgumentException e) {
                        Log.w(TAG, "No event");
                    }
                } else {
                    Log.d(TAG, "Sorry i tried my best");
                }
            }
        });

        // transfer the data from adapter to listView

        event_listview.setAdapter(adapter[0]);

        // onClick method for each event
        event_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = eventList.get(position);
                Toast.makeText(getContext(), event.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        event_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });

        adapter[0].setOnAddbillClickListener(new EventAdapter.onAddbillListener() {
            @Override
            public void onAddbillClick(Event e) {
                Intent intent = new Intent(getActivity(), EventActivity.class);
                intent.putExtra("EVENT", e);
                startActivity(intent);
            }
        });
        return view;
    }


    public void testEvents() {
        Member testMember1 = new Member("payeeGuy1", "g2g345");
        Member testMember2 = new Member("payeeGuy1", "nb23g4h5");
        ArrayList<Member> testMemberList = new ArrayList<>();
        testMemberList.add(testMember1);
        testMemberList.add(testMember2);
        Event a = new Event("12fv3j6g4", "testEvent1", testMemberList, "date1");
        eventList.add(a);
        Event b = new Event("12ff2v3g4", "testEvent2", testMemberList, "date2");
        eventList.add(b);

    }
}

//    @Override
//    public void onResume(){
//        super.onResume();
//        //OnResume Fragment
//        Log.d(TAG,"EventListFragment created!");
//
//        db = FirebaseFirestore.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();
//
//        //DB Test - Query for the user
//        Task<QuerySnapshot> taskSnap = db.collection("Users")
//                .whereEqualTo(FieldPath.documentId(), user.getUid())
//                .get();
//        QuerySnapshot querySnap = taskSnap.getResult();
//        List<DocumentSnapshot> docSnapList = querySnap.getDocuments();
//        DocumentSnapshot docSnap = docSnapList.get(0);
////        docSnap.getReference()
//        Map<String, Object> docData = docSnap.getData();
//        Set<String> keySet = docData.keySet();
//        Log.d(TAG, "Start of Query check");
//        for(String s : keySet){
//            Log.d(TAG, s);
//            Log.d(TAG, String.valueOf(docData.get(s)));
//        }
//        Log.d(TAG, "End of Query check");
//    }

    /*
    for (String id : eventIDs){
                            Task<QuerySnapshot> taskSnap = EventListFragment.this.db.collection("Events")
                                    .whereEqualTo(FieldPath.documentId(), id)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                QuerySnapshot querySnap = task.getResult();
                                                List<DocumentSnapshot> docSnapList = querySnap.getDocuments();
                                                DocumentSnapshot docSnap = docSnapList.get(0);
                                                Log.d(TAG, "Task successful");
                                                Event e = docSnap.toObject(Event.class);
                                                Log.d(TAG, e.toString());
                                                eventList.add(e);
                                                Log.d(TAG, docSnap.getId());
                                            } else {
                                                Log.d(TAG, "Sorry i tried my best");
                                            }
                                        }
                                    });
                        }


}

     */

