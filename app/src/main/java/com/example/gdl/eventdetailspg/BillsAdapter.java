package com.example.gdl.eventdetailspg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gdl.R;
import com.example.gdl.models.Bill;

import java.util.List;


public class BillsAdapter extends ArrayAdapter<Bill> {
    private int resourceId;

    //save view as cache
    class ViewHolder {
        ImageView billsImage;
        TextView billsName;
        TextView billsPayer;
        TextView billsSpending;
        TextView billsMembers;
        TextView billsStatus;
    }

    //instructor, accept data
    public BillsAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Bill> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Bill bill = getItem(position); //get event instance

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {

            // Avoid reloading the layout every time the ListView is scrolled
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            // Avoid getting the control instance every time getView() is called
            viewHolder = new ViewHolder();
            viewHolder.billsImage = view.findViewById(R.id.bill_image);
            viewHolder.billsName = view.findViewById(R.id.bill_name);
            viewHolder.billsPayer = view.findViewById(R.id.bill_payer);
            viewHolder.billsSpending = view.findViewById(R.id.bill_spending);
            viewHolder.billsMembers = view.findViewById(R.id.bill_members);
            viewHolder.billsStatus = view.findViewById(R.id.bill_status);

            // save instance of viewHolder to view
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        /*viewHolder.billsImage.setImageResource(R.drawable.ashketchum);//for test
        viewHolder.billsName.setText(bill.getName());
        viewHolder.billsPayer.setText(bill.getPayer().getName());
        viewHolder.billsMembers.setText("Members: "+bill.memberSize());
        viewHolder.billsSpending.setText("Total Spending: "+bill.getSpending());*/

        viewHolder.billsName.setText(bill.getName());
//        viewHolder.billsPayer.setText("Paid by: " + bill.getPayer().getName());
//        viewHolder.billsMembers.setText("Members: " + bill.getMemberSize());
        viewHolder.billsSpending.setText("Total Spending: "+bill.getTotalCost());

        //TODO: find a way to judge the status of bill
        /**if (bill.getStatus()){
            do some thing;
        } else {
            do some thing;
        }*/
        viewHolder.billsStatus.setText("Status: "+"Completed");
        return view;
    }
}
