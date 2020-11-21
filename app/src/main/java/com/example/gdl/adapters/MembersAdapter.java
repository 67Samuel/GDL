package com.example.gdl.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdl.R;
import com.example.gdl.models.Member;

import java.util.ArrayList;
import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> implements Filterable{

    //TODO: find out whether adapter can send selected list to the activity
    //TODO: set textview to the names of the current selected members

    private static final String TAG = "MembersAdapter";

    private ArrayList<Member> mFriendsList;
    private ArrayList<Member> mFriendsListFull; //copy of full list
    private ArrayList<Member> selectedList = new ArrayList<>(); //members selected to be in event
    Context context;

    public MembersAdapter(Context context, ArrayList<Member> friendsList) {
        this.mFriendsList = friendsList;
        this.mFriendsListFull = new ArrayList<>(friendsList);
        this.context = context;
    }

    public ArrayList<Member> getSelectedList() {
        return selectedList;
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
        holder.name.setText(mFriendsList.get(position).getName());
        if (selectedList.contains(mFriendsList.get(position))) {
            holder.itemLayout.setBackgroundColor(0x3803A762);
        } else {
            holder.itemLayout.setBackgroundColor(0xFFFFFFFF);
        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable viewColor = (ColorDrawable) holder.itemLayout.getBackground();
                int colorId = viewColor.getColor();
                Log.d(TAG, "onClick: colourid="+colorId);
                if (colorId == 0xFFFFFFFF) {
                    Toast.makeText(context, "" + mFriendsList.get(position).getName() + " added", Toast.LENGTH_SHORT).show();
                    holder.itemLayout.setBackgroundColor(0x3803A762);
                    selectedList.add(mFriendsList.get(position));
                } else if (colorId == 0x3803A762) {
                    Toast.makeText(context, "" + mFriendsList.get(position).getName() + " removed", Toast.LENGTH_SHORT).show();
                    holder.itemLayout.setBackgroundColor(0xFFFFFFFF);
                    selectedList.remove(mFriendsList.get(position));
                }
            }
        });
        holder.profilePhoto.setImageResource(R.drawable.ashketchum);
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
            mFriendsList.clear();;
            mFriendsList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        //after receiving the itemView from onCreateViewHolder, assign the required views to it
        TextView name;
        ImageView profilePhoto;
        RelativeLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.memberName);
            profilePhoto = itemView.findViewById(R.id.profile_image);
            itemLayout = itemView.findViewById(R.id.member_recyclerview_item);
        }
    }

}