package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.bsaldevs.mobileclient.Fragments.AllDevicesFragment;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnectionListener;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Fragments.RoomsFragment;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements RoomsFragment.OnFragmentInteractionListener, AllDevicesFragment.OnFragmentInteractionListener {

    private MyApplication application;
    private CoordinatorLayout coordinatorLayout;
    private long lastBackPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MyApplication) getApplication();

        initGUI();
        Thread thread = new Thread(new ServerStatusCheckThread());
        thread.start();

    }

    private void initGUI() {

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);

        LinearLayout mainLayout = findViewById(R.id.main_container);
        mainLayout.setBackgroundResource(R.drawable.change_bg_anim);

        TransitionDrawable transitionDrawable = (TransitionDrawable) mainLayout.getBackground();

        AnimationDrawable animationDrawable = (AnimationDrawable) transitionDrawable.getDrawable(0);
        animationDrawable.setEnterFadeDuration(10000);
        animationDrawable.setExitFadeDuration(10000);
        animationDrawable.start();

        ViewPager viewPager = findViewById(R.id.view_pager_container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        NavigationView navigationView = findViewById(R.id.main_navigation_view);
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

                return false;
            }
        });

        MenuItem aboutMenuButton = navigationView.getMenu().getItem(3);
        aboutMenuButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, AboutOfProgramActivity.class);
                startActivity(intent);
                return false;
            }
        });

        coordinatorLayout = findViewById(R.id.main_coordinator_layout);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragmentPage(new RoomsFragment(), "Комнаты");
        adapter.addFragmentPage(new AllDevicesFragment(), "Все устройства");

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

    @Override
    public void onBackPressed() {

        long currentTime = System.currentTimeMillis();

        Log.d("CDA", "times between back click is " + (currentTime - lastBackPressedTime));

        int EXIT_DELAY_TIME_MILLIS = 2000;
        if (currentTime - lastBackPressedTime <= EXIT_DELAY_TIME_MILLIS) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this, "Для выхода нажмите еще раз", Toast.LENGTH_SHORT).show();
        }
        lastBackPressedTime = currentTime;
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
            CheckServerStatusWrapper statusWrapper = new CheckServerStatusWrapper();
            application.getClient().subscribeToTCPListener(statusWrapper);
        }

    }

    public class CheckServerStatusWrapper implements TCPConnectionListener {

        @Override
        public void onConnectionReady(TCPConnection connection) {
            Log.d("CDA", "CheckServerStatusWrapper: onConnectionReady");
            ShowSnackBar showSnackbar = new ShowSnackBar("Соединение установлено", true);
            showSnackbar.execute();
        }

        @Override
        public void onReceiveString(TCPConnection connection, String value) {

        }

        @Override
        public void onDisconnect(TCPConnection connection) {
            Log.d("CDA", "CheckServerStatusWrapper: onDisconnect");
            ShowSnackBar showSnackbar = new ShowSnackBar("Соединение разорвано", false);
            showSnackbar.execute();
        }

        @Override
        public void onException(TCPConnection connection, Exception e) {

            String exceptionValue = String.valueOf(e);

            if (exceptionValue.contains("Connection refused")) {
                // подключение к выключенному/не существующему серверу
            } else if (exceptionValue.contains("Connection reset")) {
                // вырубился сервер
            } else if (exceptionValue.contains("Software caused connection abort")) {
                // отключился интернет на телефоне
            } else if (e.getMessage().contains("send string exception, out object is null")) {
                // объект аутпута еще не создан
            }
        }

        public class ShowSnackBar extends AsyncTask<Void, Void, Void> {

            private String text;
            private boolean isServerReachable;

            public ShowSnackBar(String text, boolean isServerReachable) {
                this.text = text;
                this.isServerReachable = isServerReachable;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (isServerReachable) {
                    Log.d("CDA", "onPostExecute: the server is reachable");
                    Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.d("CDA", "Snackbar ok clicked");
                                }
                            }).show();
                    //transitionDrawable.reverseTransition(500);
                } else {
                    Log.d("CDA", "onPostExecute: the server is not reachable");
                    Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.d("CDA", "Snackbar refresh clicked");
                                    application.reconnect();
                                }
                            }).show();
                    //transitionDrawable.startTransition(500);
                }
            }
        }

    }

}