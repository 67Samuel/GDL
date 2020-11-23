package com.example.gdl.myfriendspg;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdl.R;

import java.util.ArrayList;

public class FriendListPage extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayourManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        ArrayList<exampleItem> exampleList = new ArrayList<>();
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "3"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "6"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "9"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "3"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "6"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "9"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "3"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "6"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Name of Friend", "Shared Events:", "9"));

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayourManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayourManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
