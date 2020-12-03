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
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Marcus"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Zhi Xuan"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Mathias"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Samuel"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Zichen"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Jacob"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Hannah"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Altrive"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Cade"));
        exampleList.add(new exampleItem(R.drawable.ic_launcher_foreground, "Elizabeth"));



        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayourManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayourManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
