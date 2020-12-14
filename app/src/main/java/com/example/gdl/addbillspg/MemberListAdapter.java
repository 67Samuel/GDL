package com.example.gdl.addbillspg;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdl.R;
import com.example.gdl.models.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {
    List<Member> mMemberList;
    LayoutInflater mInflater;
    MemberViewHolder memberViewHolder;

    public MemberListAdapter(Context context, List<Member> memberList) {
        mInflater = LayoutInflater.from(context);
        this.mMemberList = memberList;
    }

    @NonNull
    @Override
    public MemberListAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mMemberView = mInflater.inflate(R.layout.add_bill_page_memberlist_member, parent, false);
        memberViewHolder = new MemberViewHolder(mMemberView, this);
        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberListAdapter.MemberViewHolder holder, int position) {
        Member m = mMemberList.get(position);
        String memberName = m.getName();
        holder.textViewMember.setText(memberName);
        holder.data = m;
    }

    @Override
    public int getItemCount() {
        return mMemberList.size();

    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "Logcat";
        RecyclerView.Adapter mAdapter;
        TextView textViewMember;
        CheckBox checkBoxPayer;
        CheckBox checkBoxPayee;
        Context context;
        Member data;
        ArrayList<String> payeeID = new ArrayList<>();
        ArrayList<String> payerID = new ArrayList<>();
        public MemberViewHolder(final View memberView, MemberListAdapter adapter) {
            super(memberView);
            textViewMember = memberView.findViewById(R.id.member);
            checkBoxPayer = memberView.findViewById(R.id.checkBoxPayer);
            checkBoxPayee = memberView.findViewById(R.id.checkBoxPayee);
            context = memberView.getContext();
            checkBoxPayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkBoxPayer.isChecked()) {

                        payerID.add(data.getId());

                        Log.i(TAG, "Add Payer: " + textViewMember.getText().toString());
                    } else {

                        payerID.remove(data.getId());
                        Log.i(TAG, "Remove Payer: " + textViewMember.getText().toString());
                    }

                }
            });
            checkBoxPayee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBoxPayee.isChecked()) {

                        payeeID.add(data.getId());

                        Log.i(TAG, "Add Payee: " + textViewMember.getText().toString());
                    } else {


                        payeeID.remove(data.getId());

                        Log.i(TAG, "Remove Payee: " + textViewMember.getText().toString());
                    }
                }
            });
        }
    }
}
