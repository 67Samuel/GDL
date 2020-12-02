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
import com.example.gdl.models.BillProcessor;

import java.util.ArrayList;
import java.util.List;


public class BillsOverviewAdapter extends ArrayAdapter<String> {
    private int resourceId;


    //save view as cache
    class ViewHolder {
        CheckBox checkBox;
        TextView billName;
    }

    //instructor, accept data
    public BillsOverviewAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<String> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String string = getItem(position); //get event instance

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

        viewHolder.billName.setText(string);
        //no way to judge the states of bills, default to be true
        viewHolder.checkBox.setChecked(true);
        return view;
    }
}
