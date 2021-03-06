package com.example.gdl.createeventpg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> implements Filterable{

    private static final String TAG = "MembersAdapter";

    private List<Member> mFriendsList;
    private List<Member> mFriendsListFull; //copy of full list for filtering
    Context context;
    private RecyclerItemSelectedListener recyclerItemSelectedListener;
    final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    StorageReference userImageStorageRef;

    public MembersAdapter(Context context, List<Member> friendsList) {
        this.mFriendsList = friendsList;
        this.mFriendsListFull = new ArrayList<>(friendsList);
        this.context = context;
        recyclerItemSelectedListener = (CreateEventSelectMembers)context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    @Override
    public Filter getFilter() {
        return friendsFilter;
    }

    private Filter friendsFilter = new Filter() { //create a Filter class
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) { //computed in background thread. charSequence will be connected to the input text
            List<Member> filteredList = new ArrayList<>(); //contains only filtered Members

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(mFriendsListFull); //if nothing is typed in, don't filter anything
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Member m : mFriendsListFull) {
                    if (m.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(m);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //takes results from performFiltering
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mFriendsList.clear();
            mFriendsList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

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
            recyclerItemSelectedListener.onItemSelected(selectedMember.getId()); //make chip
        }
    }
}