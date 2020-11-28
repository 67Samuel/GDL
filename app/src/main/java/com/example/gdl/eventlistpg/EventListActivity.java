package com.example.gdl.eventlistpg;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import com.example.gdl.ActivityWithMenu;
import com.example.gdl.createeventpg.CreateEventMain;
import com.example.gdl.JoinEventActivity;
import com.example.gdl.R;
import com.example.gdl.eventdetailspg.EventActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class EventListActivity extends ActivityWithMenu {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    FloatingActionButton floatingButton_join;
    FloatingActionButton floatingButton_create;

    //save fragments
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        tabLayout = findViewById(R.id.event_list_tablayout);
        viewPager = findViewById(R.id.event_list_viewpager);
        floatingButton_join = findViewById(R.id.eventlist_floatingbutton_join);
        floatingButton_create = findViewById(R.id.eventlist_floatingbutton_create);

        tabLayout.addTab(tabLayout.newTab().setText("Ongoing"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));

        //save instances of fragment to fragments
        fragments.add(new EventListFragment());
        fragments.add(new EventListFragment());

        viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        floatingButton_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventListActivity.this, JoinEventActivity.class);
                startActivity(intent);
            }
        });

        floatingButton_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventListActivity.this, CreateEventMain.class);
                startActivity(intent);
            }
        });

    }
}

