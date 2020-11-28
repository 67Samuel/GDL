package com.example.gdl.eventlistpg;

import android.content.Context;
import android.content.Intent;
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

import com.example.gdl.R;
import com.example.gdl.eventdetailspg.EventActivity;
import com.example.gdl.models.Event;




public class EventAdapter extends ArrayAdapter<Event> {
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

        viewHolder.eventImage.setImageResource(event.getImageId());
        viewHolder.eventName.setText(event.getEventName());
        viewHolder.eventTime.setText(event.getEventTime());
        viewHolder.eventMembers.setText("Members: "+event.getEventMembers());
        viewHolder.eventSpending.setText("Total Spending: "+event.getEventSpending());
        if (event.getStatus()){
            viewHolder.statusImage.setImageResource(R.drawable.completed);
        } else {
            viewHolder.statusImage.setImageResource(R.drawable.ongoing);
        }
        viewHolder.addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAddbillListener.onAddbillClick();
            }
        });
        return view;
    }

    public interface onAddbillListener {
        void onAddbillClick();
    }

    private onAddbillListener mOnAddbillListener;

    public void setOnAddbillClickListener(onAddbillListener mOnAddbillListener) {
        this.mOnAddbillListener = mOnAddbillListener;
    }

}
