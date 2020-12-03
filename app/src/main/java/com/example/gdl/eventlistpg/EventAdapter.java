package com.example.gdl.eventlistpg;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import com.example.gdl.Glide.GlideApp;
import com.example.gdl.R;
import com.example.gdl.eventdetailspg.EventActivity;
import com.example.gdl.models.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class EventAdapter extends ArrayAdapter<Event> {
    public static final String TAG = "EventAdapter";
    private int resourceId;

    //save view as cache
    class ViewHolder {
        ImageView statusImage;
        ImageView eventImage;
        TextView eventName;
        TextView eventTime;
        TextView eventSpending;
        TextView eventMembers;
        Button addBill;
    }

    //instructor, accept data
    public EventAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Event> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position); //get event instance

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {

            // Avoid reloading the layout every time the ListView is scrolled
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            // Avoid getting the control instance every time getView() is called
            viewHolder = new ViewHolder();
            viewHolder.eventImage = view.findViewById(R.id.event_image);
            viewHolder.eventName = view.findViewById(R.id.event_name);
            viewHolder.eventTime = view.findViewById(R.id.event_time);
            viewHolder.eventSpending = view.findViewById(R.id.event_spending);
            viewHolder.eventMembers = view.findViewById(R.id.event_members);
            viewHolder.statusImage = view.findViewById(R.id.status_image);
            viewHolder.addBill = view.findViewById(R.id.addbill_button);

            // save instance of viewHolder to view
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

//        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//        FirebaseFirestore db= FirebaseFirestore.getInstance();
//        FirebaseAuth mAuth= FirebaseAuth.getInstance();
//        FirebaseAuth.AuthStateListener mAuthListener;
//        FirebaseUser user = mAuth.getCurrentUser();

//        StorageReference imageStorageRef = storageRef.child("Profile Images");
//        Log.d(TAG, "onCreate: uid: " + user.getUid());
//        StorageReference userImageStorageRef = imageStorageRef.child(user.getUid() + ".jpg");
//        try {
//            GlideApp.with(EventAdapter.this)
//                    .load(userImageStorageRef)
//                    .into(viewHolder.eventImage);
//            Log.d(TAG, "onCreate: profile pic obtained");
//        } catch (Exception e) {
//            Log.d(TAG, "onCreate: cannot set photo");
//        }

        viewHolder.eventImage.setImageURI(event.getUriFromString());
        viewHolder.eventName.setText(event.getName());
        viewHolder.eventTime.setText(event.getDate());
        viewHolder.eventMembers.setText("Members: "+event.getMembersList().size());
        viewHolder.eventSpending.setText("Total Spending: "+event.getTotalSpent());
        if (event.getStatus()){
            viewHolder.statusImage.setImageResource(R.drawable.completed);
        } else {
            viewHolder.statusImage.setImageResource(R.drawable.ongoing);
        }
        viewHolder.addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAddbillListener.onAddbillClick(event);
            }
        });
        return view;
    }

    public interface onAddbillListener {
        void onAddbillClick(Event event);
    }

    private onAddbillListener mOnAddbillListener;

    public void setOnAddbillClickListener(onAddbillListener mOnAddbillListener) {
        this.mOnAddbillListener = mOnAddbillListener;
    }

}
