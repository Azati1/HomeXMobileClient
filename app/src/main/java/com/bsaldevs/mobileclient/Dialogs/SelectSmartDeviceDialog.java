package com.bsaldevs.mobileclient.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bsaldevs.mobileclient.DeviceGroup;
import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;

import java.util.List;

public class SelectSmartDeviceDialog extends Dialog {

    private List<SmartDevice> deviceList;
    private Context context;
    private MyApplication application;
    private DeviceType deviceType;
    private DeviceGroup deviceGroup;

    public SelectSmartDeviceDialog(@NonNull Context context, DeviceType deviceType, DeviceGroup deviceGroup) {
        super(context);
        this.context = context;
        this.deviceType = deviceType;
        this.deviceGroup = deviceGroup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_smart_device);

        application = (MyApplication) context.getApplicationContext();
        deviceList = getDevicesList(deviceType, deviceGroup);

        RecyclerView recyclerView = findViewById(R.id.recycler_select_smart_device);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);

        recyclerView.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(new Adapter());
    }

    private List<SmartDevice> getDevicesList(DeviceType deviceType, DeviceGroup deviceGroup) {
        return application.getSmartDevices(deviceType, deviceGroup);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

        @NonNull
        @Override
        public Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_smart_device_item, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {

            final SmartDevice smartDevice = deviceList.get(i);

            holder.bind(smartDevice);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), smartDevice.getDisplayActivity());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return deviceList.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private ImageView imageView;
            private Switch aSwitch;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.small_smart_device_name);
                imageView = itemView.findViewById(R.id.small_smart_device_image);
                aSwitch = itemView.findViewById(R.id.small_smart_device_toggle_button);
            }

            private void bind(final SmartDevice smartDevice) {
                name.setText(smartDevice.getName());
                imageView.setImageResource(smartDevice.getDeviceType().getImageResourceID());
                aSwitch.setChecked(smartDevice.isEnabled());
                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b)
                            smartDevice.turnOff();
                        else
                            smartDevice.turnOn();
                    }
                });
            }
        }
    }
}
