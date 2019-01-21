package com.bsaldevs.mobileclient.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.Dialogs.ConfirmAddNewDeviceDialog;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;
import java.util.ArrayList;
import java.util.List;

public class AddNewSmartDeviceActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private EditText editText;

    private List<RecyclerItem> recyclerItems;

    private MyApplication application;
    private PlaceGroup placeGroup;
    private String placeGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_new_devices);

        application = (MyApplication) getApplication();

        Intent intent = getIntent();
        placeGroupName = intent.getStringExtra("placeGroupName");

        placeGroup = application.getPlaceGroup(placeGroupName);

        searchView = findViewById(R.id.search_device_view);
        recyclerView = findViewById(R.id.recycler_view_all_device_type);

        recyclerItems = new ArrayList<>();
        loadRecyclerItems();

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(AddNewSmartDeviceActivity.this, resId);

        recyclerView.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(AddNewSmartDeviceActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(new Adapter());

    }

    private void loadRecyclerItems() {

        recyclerItems.add(new RecyclerItem(DeviceType.LUMP_BULB));
        recyclerItems.add(new RecyclerItem(DeviceType.LAMP));
        recyclerItems.add(new RecyclerItem(DeviceType.SOCKET));
        recyclerItems.add(new RecyclerItem(DeviceType.LOCKER));
        recyclerItems.add(new RecyclerItem(DeviceType.CONDITIONER));
        recyclerItems.add(new RecyclerItem(DeviceType.HEATERS));
        recyclerItems.add(new RecyclerItem(DeviceType.MUSIC_PLAYER));
        recyclerItems.add(new RecyclerItem(DeviceType.HOOVER));
        recyclerItems.add(new RecyclerItem(DeviceType.JALOUSIE));
        recyclerItems.add(new RecyclerItem(DeviceType.KETTLE));
        recyclerItems.add(new RecyclerItem(DeviceType.CAMERA));
        recyclerItems.add(new RecyclerItem(DeviceType.AIR_FILTER));
        recyclerItems.add(new RecyclerItem(DeviceType.PRINTER));
        recyclerItems.add(new RecyclerItem(DeviceType.OVEN));
        recyclerItems.add(new RecyclerItem(DeviceType.WATER_HEATER));
        recyclerItems.add(new RecyclerItem(DeviceType.COFFEE_MACHINE));
        recyclerItems.add(new RecyclerItem(DeviceType.WASHER));
        recyclerItems.add(new RecyclerItem(DeviceType.IRON));
        recyclerItems.add(new RecyclerItem(DeviceType.FAN));
        recyclerItems.add(new RecyclerItem(DeviceType.HEATED_FLOORS));
    }

    private DeviceType getSelectedDevice() {
        DeviceType deviceType = null;

        for (RecyclerItem recyclerItem : recyclerItems) {
            if (recyclerItem.selected) {
                deviceType = recyclerItem.deviceType;
                break;
            }
        }

        return deviceType;
    }

    private class RecyclerItem {

        private boolean selected;
        private DeviceType deviceType;

        public RecyclerItem(DeviceType deviceType) {
            this.deviceType = deviceType;
            this.selected = false;
        }
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

        @NonNull
        @Override
        public Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_new_smart_device_item, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {

            final RecyclerItem recyclerItem = recyclerItems.get(i);
            holder.bind(recyclerItem);

        }

        @Override
        public int getItemCount() {
            return recyclerItems.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;
            private TextView textView;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.card_device_type_image);
                textView = itemView.findViewById(R.id.card_device_type_text);

            }

            private void bind(final RecyclerItem recyclerItem) {

                final DeviceType deviceType = recyclerItem.deviceType;

                imageView.setImageResource(deviceType.getImageResourceID());
                textView.setText(deviceType.getDeviceName());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ConfirmAddNewDeviceDialog dialog = new ConfirmAddNewDeviceDialog(AddNewSmartDeviceActivity.this, placeGroup, deviceType);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {

                                /*int RESULT_OK = 23;

                                finish();
                                Intent intent = new Intent();
                                intent.putExtra("update", "true");
                                setResult(RESULT_OK, intent);*/
                            }
                        });

                        dialog.show();

                    }
                });

            }

        }
    }
}
