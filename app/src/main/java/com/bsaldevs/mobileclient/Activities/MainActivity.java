package com.bsaldevs.mobileclient.Activities;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bsaldevs.mobileclient.Devices.Component;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.CompositeDevice;
import com.bsaldevs.mobileclient.MainFragment;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.ConnectedDevice;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.Lamp;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.Locker;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.ScheduleFragment;
import com.bsaldevs.mobileclient.User.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements MainFragment.OnFragmentInteractionListener, ScheduleFragment.OnFragmentInteractionListener {

    private List<Component> components;
    private MyApplication application;
    private TCPConnection connection;
    private Client client;
    private AnimationDrawable animationDrawable;
    private TransitionDrawable transitionDrawable;
    private boolean isServerReachable = true;

    private LinearLayout mainLayout;

    private TabLayout tabLayout;

    //private ViewPagerAdapter vpAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MyApplication) getApplication();
        connection = application.getClient().getConnection();
        client = application.getClient();

        client.setOnClientException(new Client.onExceptionListener() {
            @Override
            public void onClientException(TCPConnection connection, Exception e) {
                Log.d("CDA", "onClientExceptionHandler");
            }
        });

        components = new ArrayList<>();
        loadDevices();
        for (Component component : components) {
            application.addComponent(component);
        }

        initGUI();
        /*Thread thread = new Thread(new ServerStatusCheckThread());
        thread.start();*/

    }

    private void loadDevices() {
        CompositeDevice composite1 = new CompositeDevice("Гостиная");
        composite1.add(new Lamp("Лампа в гостиной 1", connection));
        composite1.add(new Lamp("Лампа в гостиной 2", connection));
        components.add(composite1);

        CompositeDevice composite2 = new CompositeDevice("Кухня");
        composite2.add(new Lamp("Лампа в кухне 1", connection));
        composite2.add(new Lamp("Лампа в кухне 2", connection));
        composite2.add(new Lamp("Лампа в кухне 3", connection));
        components.add(composite2);

        components.add(new Lamp("Лампа 1", connection));
        components.add(new Lamp("Лампа 2", connection));
        components.add(new Lamp("Лампа 3", connection));
        components.add(new Lamp("Лампа 4", connection));
        components.add(new Locker("Замок во входной двери", connection));
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

        adapter.addFragmentPage(new MainFragment(), "Устройства");
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


    public static class ViewPagerAdapter extends FragmentPagerAdapter {

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
        private ConnectedDevice device;
        private String subTitle;
        private boolean expanded;

        public Item(ConnectedDevice device, String subTitle) {
            this.device = device;
            this.subTitle = subTitle;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public ConnectedDevice getDevice() {
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