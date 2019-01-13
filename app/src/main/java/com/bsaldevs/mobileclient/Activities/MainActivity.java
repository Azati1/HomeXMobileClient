package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Fragments.ScheduleFragment;
import com.bsaldevs.mobileclient.Fragments.RoomsFragment;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKSdk;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements RoomsFragment.OnFragmentInteractionListener, ScheduleFragment.OnFragmentInteractionListener {

    private MyApplication application;
    private TransitionDrawable transitionDrawable;
    private boolean isServerReachable = true;

    private LinearLayout mainLayout;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_main);
        application = (MyApplication) getApplication();

        initGUI();
        /*Thread thread = new Thread(new ServerStatusCheckThread());
        thread.start();*/

    }

    private void initGUI() {

        mainLayout = findViewById(R.id.main_container);
        mainLayout.setBackgroundResource(R.drawable.change_bg_anim);

        transitionDrawable = (TransitionDrawable) mainLayout.getBackground();

        AnimationDrawable animationDrawable = (AnimationDrawable) transitionDrawable.getDrawable(0);
        animationDrawable.setEnterFadeDuration(10000);
        animationDrawable.setExitFadeDuration(10000);
        animationDrawable.start();

        viewPager = findViewById(R.id.viewPagerContainer);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        navigationView = findViewById(R.id.main_navigation_view);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);

        TextView navigationViewHeaderTitle = headerView.findViewById(R.id.navigation_header_title);
        navigationViewHeaderTitle.setText("Привет, " + application.getAccount().getName() + "!");

        ImageView profile_photo = headerView.findViewById(R.id.imageView4);
        Picasso.get().load(application.getAccount().getUrlPhoto()).into(profile_photo);

        MenuItem exitMenuButton = navigationView.getMenu().getItem(4);
        exitMenuButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                VKSdk.logout();
                LoginManager.getInstance().logOut();
                application.logout();

                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);

                return true;
            }
        });

        MenuItem aboutMenuButton = navigationView.getMenu().getItem(3);
        aboutMenuButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, AboutOfProgramActivity.class);
                startActivity(intent);
                return true;
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragmentPage(new RoomsFragment(), "Комнаты");
        adapter.addFragmentPage(new ScheduleFragment(), "Расписание");

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

    private class ServerStatusCheckThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CheckServerStatus task = new CheckServerStatus();
                task.execute();
            }
        }

    }

    private class BackupData extends AsyncTask<Void, Void, Void> {

        private boolean isLoaded = false;

        @Override
        protected Void doInBackground(Void... voids) {
            downloadData();
            return null;
        }

        private void downloadData() {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            if (isLoaded) {

            }
        }
    }

    private class CheckServerStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean isReachable = checkServerConnection();
            return isReachable;
        }

        private boolean checkServerConnection() {
            //boolean isReachable = hostAvailabilityCheck();
            boolean isReachable = true;

            try {
                InetAddress.getByName("18.223.21.65").isReachable(1000);
                isReachable = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (isReachable)
                Log.d("CDA", "server is reachable");
            else
                Log.d("CDA", "server is unreachable");

            return isReachable;
        }

        /*private boolean hostAvailabilityCheck() {

            int status = 0;
            final boolean[] isReachable = {true};
            Socket s = null;

            Thread supportHostAvailablility = new Thread(new SupportThread());
            supportHostAvailablility.start();

            try {
                s = new Socket("18.223.21.65", 3346);
                status = 1;
                return true;
            } catch (IOException ex) {

            }
            status = -1;
            return false;
        }

        class SupportThread implements Runnable {

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    if (status == 1)
                        isReachable[0] = true;
                    else
                        isReachable[0] = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean checkIsHostAvailablitity(int status) {

        }*/

        @Override
        protected void onPostExecute(Boolean isReachable) {

            if (isServerReachable == isReachable)
                return;

            isServerReachable = isReachable;

            if (isReachable) {
                transitionDrawable.reverseTransition(500);
            } else {
                transitionDrawable.startTransition(500);
            }
            super.onPostExecute(isReachable);
        }

    }

}