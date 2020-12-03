/*package com.example.gdl.eventdetailspg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gdl.R;
import com.example.gdl.eventlistpg.EventAdapter;
import com.example.gdl.models.Bill;
import com.example.gdl.models.Item;
import com.example.gdl.models.Member;

import java.util.ArrayList;
import java.util.List;

public class BillsListFragment extends Fragment {

    private List<Bill> billsList=new ArrayList<>();

    BillsListFragment(List<Bill> billsList){
        super();
        this.billsList = billsList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bills_listview, container,false);

        //create adapter
        //testBills(); //initialize
        BillsAdapter adapter=new BillsAdapter(getContext(), R.layout.bill_item, billsList);

        // transfer the data from adapter to listView
        ListView listView=view.findViewById(R.id.bills_listview);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);

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


    /**public void testBills(){
        for(int i=0;i<5;i++){
            Member member = new Member("Peter", "12sdf345");
            Member member1 = new Member("John", "1233adf45");
            Member member2 = new Member("James", "123s4545");
            ArrayList<String> payeeList = new ArrayList<>();
            payeeList.add(member1.getId());
            payeeList.add(member2.getId());
            Bill bill = new Bill("12asd3", "bill"+i, member, payeeList, 100);
            billsList.add(bill);
        }
    }
}
*/