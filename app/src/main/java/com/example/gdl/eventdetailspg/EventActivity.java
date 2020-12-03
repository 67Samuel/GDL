/*package com.example.gdl.eventdetailspg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import com.example.gdl.GDLActivity;
import com.example.gdl.R;
import com.example.gdl.addbillspg.AddBillActivity;
import com.example.gdl.models.Event;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class EventActivity extends GDLActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;

    FloatingActionButton buttonAddBill;
    //save fragments
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        Intent intent = getIntent();
        Event event = intent.getParcelableExtra("EVENT");

        buttonAddBill = findViewById(R.id.event_floatingbutton);

        buttonAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBillIntent = new Intent(EventActivity.this, AddBillActivity.class);
                startActivity(addBillIntent);
            }
        });

        tabLayout = findViewById(R.id.event_tablayout);
        viewPager = findViewById(R.id.event_viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Bills"));
        tabLayout.addTab(tabLayout.newTab().setText("Members"));


        //save instances of fragment to fragments
        fragments.add(new OverviewFragment(event));
        fragments.add(new BillsListFragment(event.getBillsList()));
        fragments.add(new MembersFragment());

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

    }
}

 */