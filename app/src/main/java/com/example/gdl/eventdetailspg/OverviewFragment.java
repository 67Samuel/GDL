package com.example.gdl.eventdetailspg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gdl.R;
import com.example.gdl.models.Bill;
import com.example.gdl.models.BillProcessor;
import com.example.gdl.models.Event;
import com.example.gdl.models.GraphOptimiser;
import com.example.gdl.models.GreedyAlgorithm;
import com.example.gdl.models.Member;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends Fragment {

    private List<Bill> billsList;
    private Event event;
    private List<String> printList = new ArrayList<>();

    OverviewFragment(Event event){
        super();
        this.event = event;
        this.billsList = event.getBillsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bills_overview_fragment, container,false);

        buildPrintList();
        BillsOverviewAdapter adapter=new BillsOverviewAdapter(getContext(), R.layout.bill_item_overview, printList);

        // transfer the data from adapter to listView
        ListView listView=view.findViewById(R.id.overview_bill_listview);
        listView.setAdapter(adapter);

        // onClick method for each event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill=billsList.get(position);
                Toast.makeText(getContext(), bill.getName(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    public void buildPrintList(){
        List<Member> memberList = event.getMembersList();
        List<String> nameList = getNameFromMembersList(memberList);

        BillProcessor billProcessor = new BillProcessor(billsList);
        GraphOptimiser greedyAlgorithm = new GreedyAlgorithm();
        billProcessor.optimiseTransactions(greedyAlgorithm);

        //here should be better algorithm to get name of member
        printList = billProcessor.getAnnouncements();
    }

    public List<String> getNameFromMembersList(List<Member> memberList){
        List<String> nameList = new ArrayList<>();
        for (Member m:memberList){
            nameList.add(m.getName());
        }
        return nameList;
    }
}