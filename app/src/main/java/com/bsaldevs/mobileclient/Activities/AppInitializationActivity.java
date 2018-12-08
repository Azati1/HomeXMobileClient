package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bsaldevs.mobileclient.Fragments.UserInitializationFirstStepFragment;
import com.bsaldevs.mobileclient.Fragments.UserInitializationSecondStepFragment;
import com.bsaldevs.mobileclient.Fragments.UserInitializationThirdStepFragment;
import com.bsaldevs.mobileclient.R;
import java.util.ArrayList;
import java.util.List;

public class AppInitializationActivity extends AppCompatActivity implements
        UserInitializationFirstStepFragment.OnFragmentInteractionListener,
        UserInitializationSecondStepFragment.OnFragmentInteractionListener,
        UserInitializationThirdStepFragment.OnFragmentInteractionListener {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_initialization);

        viewPager = findViewById(R.id.initialization_pager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        AppInitializationActivity.ViewPagerAdapter adapter = new AppInitializationActivity.ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragmentPage(new UserInitializationFirstStepFragment(), "Перывй");
        adapter.addFragmentPage(new UserInitializationSecondStepFragment(), "Второй");
        adapter.addFragmentPage(new UserInitializationThirdStepFragment(), "Третий");

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

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }

    }*/

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
