package com.bsaldevs.mobileclient.Activities;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bsaldevs.mobileclient.Devices.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Net.Command;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Lamp;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Locker;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Fragments.ScheduleFragment;
import com.bsaldevs.mobileclient.Fragments.RoomsFragment;
import com.bsaldevs.mobileclient.User.Client;
import com.bsaldevs.mobileclient.User.Mobile;
import com.bsaldevs.mobileclient.User.MobileClient;
import com.bsaldevs.mobileclient.User.UserDevice;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements RoomsFragment.OnFragmentInteractionListener, ScheduleFragment.OnFragmentInteractionListener {

    private List<SmartDevice> smartDevices;
    private MyApplication application;
    private TCPConnection connection;
    private Client client;
    private AnimationDrawable animationDrawable;
    private TransitionDrawable transitionDrawable;
    private boolean isServerReachable = true;

    private LinearLayout mainLayout;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDevice mobile = new Mobile("username");

        //String ip = "localhost";
        String ip = "192.168.0.101";
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

        smartDevices = new ArrayList<>();
        loadDevices();
        for (SmartDevice smartDevice : smartDevices) {
            application.addSmartDevice(smartDevice);
        }

        initGUI();
        /*Thread thread = new Thread(new ServerStatusCheckThread());
        thread.start();*/

    }

    private void loadDevices() {

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

        /*recyclerView = findViewById(R.id.recyclerConnectedItems);

        recyclerView.setLayoutAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new Adapter());*/

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

    /*private class Item {
        private SmartDevice device;
        private String subTitle;
        private boolean expanded;

        public Item(SmartDevice device, String subTitle) {
            this.device = device;
            this.subTitle = subTitle;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public SmartDevice getDevice() {
            return device;
        }
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.connected_item, viewGroup, false);
            return new ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, final int i) {

            final Item item = items.get(i);

            holder.bind(item);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the current state of the item
                    boolean expanded = item.expanded;
                    // Change the state
                    item.setExpanded(!expanded);
                    // Notify the adapter that item has changed
                    notifyItemChanged(i);
                }
            });

            if (item.expanded) {
                holder.subTextView.setVisibility(View.VISIBLE);
            } else {
                holder.subTextView.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return devices.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;
            private TextView textView;
            private TextView subTextView;
            private Button buttonTurnOn;
            private Button buttonTurnOff;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageConnectedItem);
                textView = itemView.findViewById(R.id.textConnectedItemName);
                subTextView = itemView.findViewById(R.id.textExpandedItem);
                buttonTurnOn = itemView.findViewById(R.id.button2);
                buttonTurnOff = itemView.findViewById(R.id.button3);
            }

            private void bind(final Item item) {
                // Get the state
                boolean expanded = item.expanded;
                // Set the visibility based on state
                subTextView.setVisibility(expanded ? View.VISIBLE : View.GONE);
                buttonTurnOn.setVisibility(expanded ? View.VISIBLE : View.GONE);
                buttonTurnOff.setVisibility(expanded ? View.VISIBLE : View.GONE);
                subTextView.setText(item.getSubTitle());
                textView.setText(item.getDevice().getName());
                imageView.setImageResource(item.getDevice().getImage());
                buttonTurnOn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.getDevice().turnOn();
                    }
                });
                buttonTurnOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.getDevice().turnOff();
                    }
                });
            }
        }
    }*/

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