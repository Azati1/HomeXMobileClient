package com.bsaldevs.mobileclient.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bsaldevs.mobileclient.Fragments.DeviceGroupFragment;
import com.bsaldevs.mobileclient.Fragments.RoomsFragment;
import com.bsaldevs.mobileclient.Fragments.ScheduleFragment;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

public class DevicesActivity extends FragmentActivity implements DeviceGroupFragment.OnFragmentInteractionListener, ScheduleFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String placeGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        placeGroupName = getIntent().getStringExtra("placeGroupName");

        viewPager = findViewById(R.id.view_pager_devices);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        DeviceGroupFragment deviceGroupFragment = new DeviceGroupFragment();
        Bundle bundle = new Bundle();
        bundle.putString("placeGroupName", placeGroupName);
        deviceGroupFragment.setArguments(bundle);
        adapter.addFragmentPage(deviceGroupFragment, "Группы устройств");
        adapter.addFragmentPage(new ScheduleFragment(), "Все устройства");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View view, float v) {
                final float invt = Math.abs(Math.abs(v) - 1);
                view.setAlpha(invt);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        private List<String> titles;

        public ViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
        }

        public void addFragmentPage(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

}
