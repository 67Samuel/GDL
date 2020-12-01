package com.example.gdl.addbillspg;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdl.R;

import java.util.ArrayList;
import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {
    ArrayList<String> mMemberList;
    LayoutInflater mInflater;

    public MemberListAdapter(Context context, ArrayList<String> memberList) {
        mInflater = LayoutInflater.from(context);
        this.mMemberList = memberList;
    }

    @NonNull
    @Override
    public MemberListAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mMemberView = mInflater.inflate(R.layout.add_bill_page_memberlist_member, parent, false);
        return new MemberViewHolder(mMemberView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberListAdapter.MemberViewHolder holder, int position) {
        String mCurrent = mMemberList.get(position);
        holder.memberMemberView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mMemberList.size();

    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "Logcat";
        RecyclerView.Adapter mAdapter;
        TextView memberMemberView;
        CheckBox checkBoxPayer;
        CheckBox checkBoxPayee;
        public MemberViewHolder(final View memberView, MemberListAdapter adapter) {
            super(memberView);
            memberMemberView = memberView.findViewById(R.id.member);
            checkBoxPayer = memberView.findViewById(R.id.checkBoxPayer);
            checkBoxPayee = memberView.findViewById(R.id.checkBoxPayee);
            checkBoxPayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBoxPayer.isChecked()) {
                        // TODO 1.1 Add the String name the Payer to the ArrayList of a Bill Object

                        Log.i(TAG, "Add Payer: " + memberMemberView.getText().toString());
                    } else {
                        // TODO 1.2 Remove the string name the Payer from the ArrayList above

                        Log.i(TAG, "Remove Payer: " + memberMemberView.getText().toString());
                    }

                }
            });
            checkBoxPayee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBoxPayee.isChecked()) {
                        // TODO 2.1 Add the String name of the Payee to the ArrayList of a Bill Object

                        Log.i(TAG, "Add Payee: " + memberMemberView.getText().toString());
                    } else {
                        //
                        // TODO 2.2 Remove the string name the Payee from the ArrayList above

                        Log.i(TAG, "Remove Payee: " + memberMemberView.getText().toString());
                    }
                }
            });
        }
    }
}
