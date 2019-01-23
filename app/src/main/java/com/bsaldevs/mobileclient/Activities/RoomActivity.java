package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bsaldevs.mobileclient.DeviceGroup;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {

    private static final int DISPLAY_LINE_CAPACITY = 3;
    private String deviceGroupName;

    private MyApplication application;
    private DeviceGroup deviceGroup;
    private RecyclerView recycler;
    private List<SmartDeviceLineDisplay> deviceGroupLineDisplayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        application = (MyApplication) getApplication();

        deviceGroupName = getIntent().getStringExtra(getString(R.string.device_group_bundle_name));

        try {
            deviceGroup = application.getDeviceGroup(deviceGroupName);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CDA_DF", e.getMessage());
        }

        initGUI();

        deviceGroupLineDisplayList = loadDeviceGroupData();

    }

    private void initGUI() {

        setTitle(deviceGroupName);

        DrawerLayout deviceLayout = findViewById(R.id.drawer_layout);
        deviceLayout.setBackgroundResource(R.drawable.change_bg_anim);

        TransitionDrawable transitionDrawable = (TransitionDrawable) deviceLayout.getBackground();

        AnimationDrawable animationDrawable = (AnimationDrawable) transitionDrawable.getDrawable(0);
        animationDrawable.setEnterFadeDuration(10000);
        animationDrawable.setExitFadeDuration(10000);
        animationDrawable.start();

        recycler = findViewById(R.id.recycler_all_devices);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(RoomActivity.this, resId);

        recycler.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(RoomActivity.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(horizontalLayoutManager);
        recycler.setAdapter(new Adapter());
    }

    private List<SmartDeviceLineDisplay> loadDeviceGroupData() {

        List<SmartDeviceLineDisplay> smartDeviceLineDisplayList = new ArrayList<>();
        List<SmartDevice> smartDevices = deviceGroup.getDevicesInside();

        for (int i = 0;; i++) {

            if (i * DISPLAY_LINE_CAPACITY >= smartDevices.size())
                break;

            SmartDeviceLineDisplay smartDeviceLineDisplay = new SmartDeviceLineDisplay();

            for (int j = 0; j < DISPLAY_LINE_CAPACITY; j++) {

                if (i * DISPLAY_LINE_CAPACITY + j >= smartDevices.size())
                    break;

                SmartDevice smartDevice = smartDevices.get(i * DISPLAY_LINE_CAPACITY + j);
                smartDeviceLineDisplay.addCardDeviceToLine(smartDevice);
            }

            smartDeviceLineDisplayList.add(smartDeviceLineDisplay);

        }

        return smartDeviceLineDisplayList;
    }

    private class SmartDeviceLineDisplay {

        private List<SmartDevice> smartDevices;

        public SmartDeviceLineDisplay() {
            smartDevices = new ArrayList<>();
        }

        public void addCardDeviceToLine(SmartDevice smartDevice) {
            smartDevices.add(smartDevice);
        }

        public List<SmartDevice> getSmartDevices() {
            return smartDevices;
        }

    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

        @NonNull
        @Override
        public Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_devices_line_view, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {

            final SmartDeviceLineDisplay smartDeviceLineDisplay = deviceGroupLineDisplayList.get(i);
            final List<SmartDevice> smartDevices = smartDeviceLineDisplay.getSmartDevices();

            holder.bind(smartDeviceLineDisplay);

            final List<CardView> cardViews = holder.cardViews;

            for (int j = 0; j < smartDevices.size(); j++) {

                final SmartDevice smartDevice = smartDevices.get(j);
                final CardView cardView = cardViews.get(j);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RoomActivity.this, smartDevice.getDisplayActivity());
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return deviceGroupLineDisplayList.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private List<TextView> textViewsName;
            private List<ImageView> imageViewsPicture;
            private List<CardView> cardViews;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewsName = new ArrayList<>();
                imageViewsPicture = new ArrayList<>();
                cardViews = new ArrayList<>();

                CardView cardView1 = itemView.findViewById(R.id.card_view_smart_device_line_item1);
                CardView cardView2 = itemView.findViewById(R.id.card_view_smart_device_line_item2);
                CardView cardView3 = itemView.findViewById(R.id.card_view_smart_device_line_item3);

                cardView1.setVisibility(View.INVISIBLE);
                cardView2.setVisibility(View.INVISIBLE);
                cardView3.setVisibility(View.INVISIBLE);

                cardViews.add(cardView1);
                cardViews.add(cardView2);
                cardViews.add(cardView3);

                for (int i = 0; i < DISPLAY_LINE_CAPACITY; i++) {

                    CardView cardView = cardViews.get(i);

                    ImageView imageView = cardView.findViewById(R.id.card_smart_device_image);
                    TextView textView = cardView.findViewById(R.id.card_smart_device_text);

                    textViewsName.add(textView);
                    imageViewsPicture.add(imageView);
                }

            }

            private void bind(final SmartDeviceLineDisplay smartDeviceLineDisplay) {

                List<SmartDevice> smartDevices = smartDeviceLineDisplay.getSmartDevices();

                for (int i = 0; i < smartDevices.size(); i++) {
                    SmartDevice smartDevice = smartDevices.get(i);

                    CardView cardView = cardViews.get(i);
                    cardView.setVisibility(View.VISIBLE);

                    TextView textView = textViewsName.get(i);
                    textView.setText(smartDevice.getName());
                    textView.setSelected(true);

                    ImageView imageView = imageViewsPicture.get(i);
                    imageView.setImageResource(smartDevice.getDeviceType().getImageResourceID());
                }

                if (smartDevices.size() != DISPLAY_LINE_CAPACITY) {
                    installAddNewDevicesCard(smartDevices.size());
                }

            }

            private void installAddNewDevicesCard(int index) {
                CardView cardView = cardViews.get(index);
                cardView.setVisibility(View.VISIBLE);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int RESULT_OK = 23;
                        Intent intent = new Intent(RoomActivity.this, AddNewSmartDeviceActivity.class);
                        intent.putExtra("deviceGroupName", deviceGroupName);
                        startActivityForResult(intent, RESULT_OK);
                    }
                });

                TextView textView = textViewsName.get(index);
                textView.setText("Добавить");

                ImageView imageView = imageViewsPicture.get(index);
                imageView.setImageResource(R.drawable.ic_plus);
            }

        }

        public void clearRecyclerView() {
            final int size = deviceGroupLineDisplayList.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    deviceGroupLineDisplayList.remove(0);
                }

                notifyItemRangeRemoved(0, size);

            }
        }

    }

}