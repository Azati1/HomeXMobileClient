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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bsaldevs.mobileclient.Devices.SmartDevices.Conditioner;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Floor;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Kettle;
import com.bsaldevs.mobileclient.Devices.SmartDevices.MusicPlayer;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Blind;
import com.bsaldevs.mobileclient.Devices.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Socket;
import com.bsaldevs.mobileclient.Net.Command;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Lamp;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Locker;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Fragments.ScheduleFragment;
import com.bsaldevs.mobileclient.Fragments.RoomsFragment;
import com.bsaldevs.mobileclient.User.Client;
import com.bsaldevs.mobileclient.User.Mobile;
import com.bsaldevs.mobileclient.User.MobileClient;
import com.bsaldevs.mobileclient.User.UserDevice;
import com.vk.sdk.VKSdk;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements RoomsFragment.OnFragmentInteractionListener, ScheduleFragment.OnFragmentInteractionListener {

    private MyApplication application;
    private TCPConnection connection;
    private Client client;
    private AnimationDrawable animationDrawable;
    private TransitionDrawable transitionDrawable;
    private boolean isServerReachable = true;

    private LinearLayout mainLayout;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDevice mobile = new Mobile("username");

        //String ip = "localhost";
        String ip = "192.168.0.100";
        int port = 3346;

        application = (MyApplication) getApplication();
        application.setupClient(new MobileClient(ip, port, mobile));
        connection = application.getClient().getConnection();
        client = application.getClient();

        client.setOnClientException(new Client.onExceptionListener() {
            @Override
            public void onClientException(TCPConnection connection, Exception e) {
                Log.d("CDA", "onClientExceptionHandler");
            }
        });

        loadPlaceGroups();
        loadDevices();

        initGUI();
        /*Thread thread = new Thread(new ServerStatusCheckThread());
        thread.start();*/

    }

    private void loadPlaceGroups() {
        PlaceGroup placeGroupOffice = new PlaceGroup("Офис", R.drawable.ic_desk);
        PlaceGroup placeGroupLivingRoom = new PlaceGroup("Гостиная", R.drawable.ic_livingroom);
        PlaceGroup placeGroupBathroom = new PlaceGroup("Ванная", R.drawable.ic_bathtub);
        PlaceGroup placeGroupBedroom = new PlaceGroup("Спальня", R.drawable.ic_bed);
        PlaceGroup placeGroupRestroom = new PlaceGroup("Уборная", R.drawable.ic_toilet);
        PlaceGroup placeGroupKitchen = new PlaceGroup("Кухня", R.drawable.ic_coffee_machine);

        application.addPlaceGroup(placeGroupOffice);
        application.addPlaceGroup(placeGroupLivingRoom);
        application.addPlaceGroup(placeGroupBathroom);
        application.addPlaceGroup(placeGroupBedroom);
        application.addPlaceGroup(placeGroupRestroom);
        application.addPlaceGroup(placeGroupKitchen);
    }

    private void loadDevices() {

        List<PlaceGroup> placeGroups = application.getPlaceGroups();

        Lamp lamp1 = new Lamp("Лампа 1", placeGroups.get(0), connection);
        Lamp lamp2 = new Lamp("Лампа 2", placeGroups.get(0), connection);
        Lamp lamp3 = new Lamp("Лампа 3", placeGroups.get(0), connection);

        application.addSmartDevice(lamp1);
        application.addSmartDevice(lamp2);
        application.addSmartDevice(lamp3);

        Socket socket1 = new Socket("Розетка 1", placeGroups.get(1), connection);
        Socket socket2 = new Socket("Розетка 2", placeGroups.get(1), connection);
        Socket socket3 = new Socket("Розетка 3", placeGroups.get(1), connection);

        application.addSmartDevice(socket1);
        application.addSmartDevice(socket2);
        application.addSmartDevice(socket3);

        Locker locker1 = new Locker("Замок 1", placeGroups.get(2), connection);
        Locker locker2 = new Locker("Замок 2", placeGroups.get(2), connection);
        Locker locker3 = new Locker("Замок 3", placeGroups.get(2), connection);

        application.addSmartDevice(locker1);
        application.addSmartDevice(locker2);
        application.addSmartDevice(locker3);

        Locker locker4 = new Locker("Замок 4", placeGroups.get(2), connection);
        Locker locker5 = new Locker("Замок 5", placeGroups.get(2), connection);
        Locker locker6 = new Locker("Замок 6", placeGroups.get(2), connection);

        application.addSmartDevice(locker4);
        application.addSmartDevice(locker5);
        application.addSmartDevice(locker6);

        Conditioner conditioner1 = new Conditioner("Кондей 1", placeGroups.get(3), connection);
        Conditioner conditioner2 = new Conditioner("Кондей 2", placeGroups.get(3), connection);
        Conditioner conditioner3 = new Conditioner("Кондей 3", placeGroups.get(3), connection);

        application.addSmartDevice(conditioner1);
        application.addSmartDevice(conditioner2);
        application.addSmartDevice(conditioner3);

        MusicPlayer musicPlayer1 = new MusicPlayer("Плеер 1", placeGroups.get(5), connection);
        MusicPlayer musicPlayer2 = new MusicPlayer("Плеер 2", placeGroups.get(5), connection);
        MusicPlayer musicPlayer3 = new MusicPlayer("Плеер 3", placeGroups.get(5), connection);

        application.addSmartDevice(musicPlayer1);
        application.addSmartDevice(musicPlayer2);
        application.addSmartDevice(musicPlayer3);

        Blind Blind1 = new Blind("Шторы 1", placeGroups.get(0), connection);
        Blind Blind2 = new Blind("Шторы 2", placeGroups.get(1), connection);
        Blind Blind3 = new Blind("Шторы 3", placeGroups.get(3), connection);
        Blind Blind4 = new Blind("Шторы 3", placeGroups.get(5), connection);

        application.addSmartDevice(Blind1);
        application.addSmartDevice(Blind2);
        application.addSmartDevice(Blind3);
        application.addSmartDevice(Blind4);

        Kettle Kettle1 = new Kettle("Чайник 1", placeGroups.get(0), connection);
        Kettle Kettle2 = new Kettle("Чайник 2", placeGroups.get(3), connection);
        Kettle Kettle3 = new Kettle("Чайник 3", placeGroups.get(5), connection);

        application.addSmartDevice(Kettle1);
        application.addSmartDevice(Kettle2);
        application.addSmartDevice(Kettle3);

        Floor Floor1 = new Floor("Подогрев 1", placeGroups.get(0), connection);
        Floor Floor2 = new Floor("Подогрев 2", placeGroups.get(1), connection);
        Floor Floor3 = new Floor("Подогрев 3", placeGroups.get(2), connection);
        Floor Floor4 = new Floor("Подогрев 4", placeGroups.get(3), connection);
        Floor Floor5 = new Floor("Подогрев 5", placeGroups.get(4), connection);
        Floor Floor6 = new Floor("Подогрев 6", placeGroups.get(5), connection);

        application.addSmartDevice(Floor1);
        application.addSmartDevice(Floor2);
        application.addSmartDevice(Floor3);
        application.addSmartDevice(Floor4);
        application.addSmartDevice(Floor5);
        application.addSmartDevice(Floor6);
    }

    private void initGUI() {

        mainLayout = findViewById(R.id.main_container);
        mainLayout.setBackgroundResource(R.drawable.change_bg_anim);

        transitionDrawable = (TransitionDrawable) mainLayout.getBackground();

        animationDrawable = (AnimationDrawable) transitionDrawable.getDrawable(0);
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

        MenuItem exitMenuButton = navigationView.getMenu().getItem(3);
        exitMenuButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                VKSdk.logout();

                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);

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
            Command command = new Command("getSmartDevicesList");
            Request request = new Request("username", "cloudServer", command);

            connection.sendRequest(request);
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