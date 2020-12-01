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
import com.example.gdl.models.Member;

import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends Fragment {

    private List<Bill> billsList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bills_overview_fragment, container,false);

        // get the date and use it to create adapter
        testBills(); //initialize
        BillsOverviewAdapter adapter=new BillsOverviewAdapter(getContext(), R.layout.bill_item_overview, billsList);

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

    public void testBills(){
        for(int i=0;i<5;i++){
            Member member = new Member("Peter", "12sdf345");
            Member member1 = new Member("John", "1233adf45");
            Member member2 = new Member("James", "123s4545");
            ArrayList<Member> payeeList = new ArrayList<>();
            payeeList.add(member1);
            payeeList.add(member2);
            Bill bill = new Bill("12asd3", "bill"+i, member, payeeList, 100);
            billsList.add(bill);
        }
    }
}