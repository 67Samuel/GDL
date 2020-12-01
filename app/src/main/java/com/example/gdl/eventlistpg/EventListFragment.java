package com.example.gdl.eventlistpg;

import android.content.Intent;
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

import com.example.gdl.createeventpg.CreateEventMain;
import com.example.gdl.eventdetailspg.EventActivity;
import com.example.gdl.eventlistpg.EventAdapter;
import com.example.gdl.models.Event;
import com.example.gdl.R;
import com.example.gdl.models.Member;

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
                Toast.makeText(getContext(), event.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnAddbillClickListener(new EventAdapter.onAddbillListener() {
            @Override
            public void onAddbillClick() {
                Intent intent = new Intent(getActivity(), EventActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    public void testEvents(){
        for(int i=0;i<5;i++){
            Member testMember1 = new Member("payeeGuy1", "g2g345");
            Member testMember2 = new Member("payeeGuy1", "nb23g4h5");
            ArrayList<Member> testMemberList = new ArrayList<>();
            testMemberList.add(testMember1);
            testMemberList.add(testMember2);
            Event a=new Event("12fv3j6g4", "testEvent1", testMemberList, "date1");
            EventList.add(a);
            Event b=new Event("12ff2v3g4", "testEvent2", testMemberList, "date2");
            EventList.add(b);
        }
    }
}