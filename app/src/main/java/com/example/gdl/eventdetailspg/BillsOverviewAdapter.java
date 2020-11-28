package com.example.gdl.eventdetailspg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gdl.R;
import com.example.gdl.models.Bill;

import java.util.List;


public class BillsOverviewAdapter extends ArrayAdapter<Bill> {
    private int resourceId;

    //save view as cache
    class ViewHolder {
        CheckBox checkBox;
        TextView billName;
    }

    //instructor, accept data
    public BillsOverviewAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Bill> objects) {
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
            viewHolder.checkBox = view.findViewById(R.id.bill_check_box);
            viewHolder.billName = view.findViewById(R.id.overview_billname);

            // save instance of viewHolder to view
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.billName.setText(bill.getName());

        //TODO: find a way to judge the status of bill
        /**if (bill.getStatus()){
            do some thing;
        } else {
            do some thing;
        }*/
        viewHolder.checkBox.setChecked(true);
        return view;
    }
}
