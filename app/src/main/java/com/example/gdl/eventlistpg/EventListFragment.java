package com.example.gdl.eventlistpg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.gdl.eventlistpg.EventAdapter;
import com.example.gdl.models.Event;
import com.example.gdl.R;

public class EventListFragment extends Fragment {

    //save event data
    private List<Event> EventList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_listview, container,false);

        // get the date and use it to create adapter
        testEvents(); //initialize
        EventAdapter adapter=new EventAdapter(getContext(), R.layout.event_item, EventList);

        // transfer the data from adapter to listView
        ListView listView=view.findViewById(R.id.event_listview);
        listView.setAdapter(adapter);

        // onClick method for each event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event=EventList.get(position);
                Toast.makeText(getContext(), event.getEventName(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }


    public void testEvents(){
        for(int i=0;i<5;i++){
            Event a=new Event("Event a", "07/11/2020", "20", "3", R.drawable.ashketchum, true);
            EventList.add(a);
            Event b=new Event("Event b", "08/11/2020", "15", "4", R.drawable.ashketchum, false);
            EventList.add(b);
        }
    }
}