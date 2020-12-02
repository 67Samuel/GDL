package com.example.gdl.eventlistpg;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import com.example.gdl.GDLActivity;
import com.example.gdl.createeventpg.CreateEventMain;
import com.example.gdl.JoinEventActivity;
import com.example.gdl.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventListActivity extends GDLActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    FloatingActionButton floatingButton_join;
    FloatingActionButton floatingButton_create;
    FloatingActionButton floatingButton_open;
    Boolean isFABOpen = false;
    TextView FAB_create_text;
    TextView FAB_join_text;

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
        floatingButton_open = findViewById(R.id.open_FAB_btn);
        FAB_create_text = findViewById(R.id.create_event_text);
        FAB_join_text = findViewById(R.id.join_event_text);

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

        floatingButton_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

    }


    private void showFABMenu() {
        isFABOpen = true;
        floatingButton_create.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        floatingButton_join.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
        floatingButton_open.animate().rotation(45);
        FAB_join_text.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
        FAB_create_text.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        FAB_join_text.setVisibility(View.VISIBLE);
        FAB_create_text.setVisibility(View.VISIBLE);
    }

    private void closeFABMenu() {
        isFABOpen = false;
        floatingButton_create.animate().translationY(0);
        floatingButton_join.animate().translationY(0);
        floatingButton_open.animate().rotation(0);
        FAB_join_text.animate().translationY(0);
        FAB_create_text.animate().translationY(0);
        FAB_create_text.setVisibility(View.GONE);
        FAB_join_text.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }

}

