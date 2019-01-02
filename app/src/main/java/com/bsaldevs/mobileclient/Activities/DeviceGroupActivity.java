package com.bsaldevs.mobileclient.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bsaldevs.mobileclient.Devices.DeviceType;
import com.bsaldevs.mobileclient.Dialogs.ConfirmEnableDeviceGroup;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Dialogs.SelectSmartDeviceDialog;
import java.util.ArrayList;
import java.util.List;

public class DeviceGroupActivity extends Activity {

    private RecyclerView recyclerDeviceGroup;
    private PlaceGroup placeGroup;
    private List<DeviceGroupLineDisplay> deviceGroupLineDisplayList;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_group);

        application = (MyApplication) getApplication();

        String placeGroupString = getIntent().getStringExtra("placeGroupName");

        placeGroup = application.getPlaceGroup(placeGroupString);

        deviceGroupLineDisplayList = loadDeviceGroupData();

        recyclerDeviceGroup = findViewById(R.id.device_group_recycler);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(DeviceGroupActivity.this, resId);

        recyclerDeviceGroup.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerDeviceGroup.setLayoutManager(horizontalLayoutManager);
        recyclerDeviceGroup.setAdapter(new Adapter());
    }

    private List<DeviceGroupLineDisplay> loadDeviceGroupData() {
        List<DeviceGroupLineDisplay> deviceGroupLineDisplays = new ArrayList<>();

        DeviceGroup deviceGroupLamp = new DeviceGroup(DeviceType.LAMP, "Освещение", R.drawable.lamp_on);
        DeviceGroup deviceGroupSocket = new DeviceGroup(DeviceType.SOCKET,"Розетки", R.drawable.ic_socket);
        DeviceGroupLineDisplay deviceGroupLineDisplay1 = new DeviceGroupLineDisplay(deviceGroupLamp, deviceGroupSocket);

        DeviceGroup deviceGroupLocker = new DeviceGroup(DeviceType.LOCKER,"Замки", R.drawable.ic_lock);
        DeviceGroup deviceGroupConditioner = new DeviceGroup(DeviceType.CONDITIONER,"Кондиционеры", R.drawable.ic_air_conditioner);
        DeviceGroupLineDisplay deviceGroupLineDisplay2 = new DeviceGroupLineDisplay(deviceGroupLocker, deviceGroupConditioner);

        DeviceGroup deviceGroupThermometer = new DeviceGroup(DeviceType.HEATERS,"Обогрев", R.drawable.ic_thermometer);
        DeviceGroup deviceGroupMusicPlayer = new DeviceGroup(DeviceType.PLAYER,"Окружение", R.drawable.ic_sound_system);
        DeviceGroupLineDisplay deviceGroupLineDisplay3 = new DeviceGroupLineDisplay(deviceGroupThermometer, deviceGroupMusicPlayer);

        DeviceGroup deviceGroupHoover = new DeviceGroup(DeviceType.HOOVER,"Пылесос", R.drawable.ic_hoover);
        DeviceGroup deviceGroupBlind = new DeviceGroup(DeviceType.JALOUSIE,"Шторы", R.drawable.ic_window);
        DeviceGroupLineDisplay deviceGroupLineDisplay4 = new DeviceGroupLineDisplay(deviceGroupHoover, deviceGroupBlind);

        DeviceGroup deviceGroupCamera = new DeviceGroup(DeviceType.CAMERA,"Камеры", R.drawable.ic_security_cam);
        DeviceGroup deviceGroupKettle = new DeviceGroup(DeviceType.KETTLE,"Чайник", R.drawable.ic_kettle);
        DeviceGroupLineDisplay deviceGroupLineDisplay5 = new DeviceGroupLineDisplay(deviceGroupCamera, deviceGroupKettle);

        deviceGroupLineDisplays.add(deviceGroupLineDisplay1);
        deviceGroupLineDisplays.add(deviceGroupLineDisplay2);
        deviceGroupLineDisplays.add(deviceGroupLineDisplay3);
        deviceGroupLineDisplays.add(deviceGroupLineDisplay4);
        deviceGroupLineDisplays.add(deviceGroupLineDisplay5);

        return deviceGroupLineDisplays;
    }

    private class DeviceGroup {

        private String name;
        private int imageResId;
        private DeviceType deviceType;

        public DeviceGroup(DeviceType deviceType, String name, int imageResId) {
            this.deviceType = deviceType;
            this.imageResId = imageResId;
            this.name = name;
        }
    }

    private class DeviceGroupLineDisplay {

        private DeviceGroup deviceGroup1;
        private DeviceGroup deviceGroup2;

        public DeviceGroupLineDisplay(DeviceGroup deviceGroup1, DeviceGroup deviceGroup2) {
            this.deviceGroup1 = deviceGroup1;
            this.deviceGroup2 = deviceGroup2;
        }
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

        @NonNull
        @Override
        public Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_group_line_view, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {

            final DeviceGroupLineDisplay deviceGroupLineDisplay = deviceGroupLineDisplayList.get(i);

            holder.bind(deviceGroupLineDisplay);

            holder.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectSmartDeviceDialog dialog = new SelectSmartDeviceDialog(DeviceGroupActivity.this, deviceGroupLineDisplay.deviceGroup1.deviceType, placeGroup);
                    dialog.show();
                }
            });

            holder.cardView1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Toast.makeText(getApplicationContext(), "Вкл/выкл", Toast.LENGTH_SHORT).show();
                    ConfirmEnableDeviceGroup dialog = new ConfirmEnableDeviceGroup(DeviceGroupActivity.this, false) {
                        @Override
                        public void setOnConfirmListener(boolean deviceGroupEnabled) {
                            if (deviceGroupEnabled)
                                Toast.makeText(getContext(), "Выключено", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), "Включено", Toast.LENGTH_SHORT).show();
                        }
                    };
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    return true;
                }
            });

            holder.cardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectSmartDeviceDialog dialog = new SelectSmartDeviceDialog(DeviceGroupActivity.this, deviceGroupLineDisplay.deviceGroup2.deviceType, placeGroup);
                    dialog.show();
                }
            });

            holder.cardView2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Toast.makeText(getApplicationContext(), "Вкл/выкл", Toast.LENGTH_SHORT).show();
                    ConfirmEnableDeviceGroup dialog = new ConfirmEnableDeviceGroup(DeviceGroupActivity.this, true) {
                        @Override
                        public void setOnConfirmListener(boolean deviceGroupEnabled) {
                            if (deviceGroupEnabled)
                                Toast.makeText(getContext(), "Выключено", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), "Включено", Toast.LENGTH_SHORT).show();
                        }
                    };
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return deviceGroupLineDisplayList.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView name1;
            private TextView name2;
            private ImageView image1;
            private ImageView image2;
            private CardView cardView1;
            private CardView cardView2;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                name1 = itemView.findViewById(R.id.device_group_name1);
                image1 = itemView.findViewById(R.id.device_group_image1);
                name2 = itemView.findViewById(R.id.device_group_name2);
                image2 = itemView.findViewById(R.id.device_group_image2);
                cardView1 = itemView.findViewById(R.id.cardview_device_group1);
                cardView2 = itemView.findViewById(R.id.cardview_device_group2);
            }

            private void bind(final DeviceGroupLineDisplay deviceGroupLineDisplay) {
                DeviceGroup deviceGroup1 = deviceGroupLineDisplay.deviceGroup1;
                DeviceGroup deviceGroup2 = deviceGroupLineDisplay.deviceGroup2;

                name1.setText(deviceGroup1.name);
                image1.setImageResource(deviceGroup1.imageResId);
                name2.setText(deviceGroup2.name);
                image2.setImageResource(deviceGroup2.imageResId);
            }
        }
    }
}
