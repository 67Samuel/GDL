package com.example.gdl.myfriendspg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdl.Glide.GlideApp;
import com.example.gdl.R;
import com.example.gdl.models.Member;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.gdl.myfriendspg.RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private List<Member> mFriendsList;
    Context context;
    final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    StorageReference userImageStorageRef;

    public RecyclerViewAdapter(Context context, List<Member> friendsList) {
        this.mFriendsList = friendsList;
        this.context = context;
    }

    @NonNull
    @Override
    public com.example.gdl.myfriendspg.RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_item, parent, false);
        return new com.example.gdl.myfriendspg.RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.gdl.myfriendspg.RecyclerViewAdapter.ViewHolder holder, int position) {
        //bind specific data to the views in each itemView
        holder.name.setText((String)mFriendsList.get(position).getName());
        Log.d(TAG, "onCreate: getting profile pic from storage");
        StorageReference imageStorageRef = storageRef.child("Profile Images");
        Log.d(TAG, "onCreate: uid: "+mFriendsList.get(position).getId());
        userImageStorageRef = imageStorageRef.child(mFriendsList.get(position).getId() + ".jpg");
        GlideApp.with(holder.profilePhoto.getContext())
                .load(userImageStorageRef)
                .into(holder.profilePhoto);
        //holder.profilePhoto.setImageURI(mFriendsList.get(position).getUriFromString());
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //after receiving the itemView from onCreateViewHolder, assign the required views to it
        TextView name;
        CircleImageView profilePhoto;
        CardView itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.memberName);
            profilePhoto = itemView.findViewById(R.id.profile_image);
            itemLayout = itemView.findViewById(R.id.member_recyclerView_item);
            itemLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Member selectedMember = mFriendsList.get(getAdapterPosition());

        }
    }
}

/*public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ExampleViewHolder> {

    private ArrayList<Member> friendsList;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.profilePic);
            mTextView1 = itemView.findViewById(R.id.friendName);
            mTextView2 = itemView.findViewById(R.id.sharedEvents);
            mTextView3 = itemView.findViewById(R.id.no_of_shared_events);
        }
    }

    public RecyclerViewAdapter(ArrayList<Member> friendsList) {
        this.friendsList = friendsList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        exampleItem currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        holder.mTextView3.setText(currentItem.getText3());


    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}
*/
