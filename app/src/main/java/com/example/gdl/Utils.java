package com.example.gdl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gdl.models.Bill;
import com.example.gdl.models.Event;
import com.example.gdl.models.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

public class Utils {

    private static final String TAG = "DBUtils";

    public Date dbUtilsDate = new Date();

    ArrayList<String> listOfIds;
    ArrayList<Member> listOfMembers = new ArrayList<>();
    ArrayList<Bill> listOfBills = new ArrayList<>();
    ArrayList<Event> listOfEvents = new ArrayList<>();

    public void waitForDB(long maxTimeAllotted) {
        long startTime = 0;
        long endTime = dbUtilsDate.getTime() + maxTimeAllotted;
        Log.d(TAG, "waitForDB: wait started");
        while((endTime-startTime)>0) {
            Log.d(TAG, "waitForDB: updating time... "+startTime);
            startTime = dbUtilsDate.getTime();
        }
        Log.d(TAG, "waitForDB: wait finished");
    }

    //method that takes in a document ref to an array and the key of the array and returns the list of ids
    public ArrayList<String> getListOfIdsFromDoc(DocumentReference documentReference, String key) {
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        listOfIds = (ArrayList<String>) document.getData().get(key);
                        } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return listOfIds;
    }

    //method that takes in a collection ref, list of ids and returns a list of Member
    public ArrayList<Member> getListOfMembersFromCollection(CollectionReference collectionReference, ArrayList<String> listOfIds) {
        listOfMembers.clear();
        for (String id : listOfIds) {
            DocumentReference objectRef = collectionReference.document(id);
            objectRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    listOfMembers.add(documentSnapshot.toObject(Member.class));
                }
            });
        }
        return listOfMembers;
    }

    //method that takes in a collection ref, list of ids and returns a list of Bills
    public ArrayList<Bill> getListOfBillsFromCollection(CollectionReference collectionReference, ArrayList<String> listOfIds) {
        listOfBills.clear();
        for (String id : listOfIds) {
            DocumentReference objectRef = collectionReference.document(id);
            objectRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    listOfBills.add(documentSnapshot.toObject(Bill.class));
                }
            });
        }
        return listOfBills;
    }

    //method that takes in a collection ref, list of ids and returns a list of Events
    public ArrayList<Event> getListOfEventsFromCollection(CollectionReference collectionReference, ArrayList<String> listOfIds) {
        listOfEvents.clear();
        for (String id : listOfIds) {
            DocumentReference objectRef = collectionReference.document(id);
            objectRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    listOfEvents.add(documentSnapshot.toObject(Event.class));
                }
            });
        }
        return listOfEvents;
    }

    public Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }

}
