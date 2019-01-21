package com.bsaldevs.mobileclient.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.Dialogs.ConfirmEnableDeviceGroupDialog;
import com.bsaldevs.mobileclient.Dialogs.SelectSmartDeviceDialog;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceGroupFragment extends android.support.v4.app.Fragment {

    private static final int DISPLAY_LINE_CAPACITY = 2;
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerDeviceGroup;
    private PlaceGroup placeGroup;
    private List<DeviceGroupLineDisplay> deviceGroupLineDisplayList;
    private MyApplication application;
    private String placeGroupName;

    public DeviceGroupFragment() {

    }

    public static DeviceGroupFragment newInstance(String placeGroupName) {
        DeviceGroupFragment fragment = new DeviceGroupFragment();
        Bundle args = new Bundle();
        args.putString("placeGroupName", placeGroupName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (MyApplication) getContext().getApplicationContext();

        placeGroupName = getArguments().getString("placeGroupName");
        placeGroup = application.getPlaceGroup(placeGroupName);

        deviceGroupLineDisplayList = loadDeviceGroupData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_group, container, false);

        recyclerDeviceGroup = view.findViewById(R.id.device_group_recycler);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

        recyclerDeviceGroup.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerDeviceGroup.setLayoutManager(horizontalLayoutManager);
        recyclerDeviceGroup.setAdapter(new Adapter());
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private List<DeviceGroupLineDisplay> loadDeviceGroupData() {

        List<DeviceGroupLineDisplay> deviceGroupLineDisplays = new ArrayList<>();
        List<DeviceType> deviceTypes = Arrays.asList(DeviceType.values());

        for (int i = 0;; i++) {

            if (i * DISPLAY_LINE_CAPACITY >= deviceTypes.size())
                break;

            DeviceGroupLineDisplay deviceGroupLineDisplay = new DeviceGroupLineDisplay();

            for (int j = 0; j < DISPLAY_LINE_CAPACITY; j++) {

                if (i * DISPLAY_LINE_CAPACITY + j >= deviceTypes.size())
                    break;

                DeviceType deviceType = deviceTypes.get(i * DISPLAY_LINE_CAPACITY + j);
                deviceGroupLineDisplay.addCardDeviceGroupToLine(deviceType);
            }

            deviceGroupLineDisplays.add(deviceGroupLineDisplay);

        }

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

        private List<DeviceType> deviceTypes;

        public DeviceGroupLineDisplay() {
            this.deviceTypes = new ArrayList<>();
        }

        public void addCardDeviceGroupToLine(DeviceType deviceType) {
            deviceTypes.add(deviceType);
        }

        public List<DeviceType> getDeviceTypes () {
            return deviceTypes;
        }
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

        @NonNull
        @Override
        public Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_device_group_line_view, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {

            final DeviceGroupLineDisplay deviceGroupLineDisplay = deviceGroupLineDisplayList.get(i);
            final List<DeviceType> deviceTypes = deviceGroupLineDisplay.getDeviceTypes();

            holder.bind(deviceGroupLineDisplay);

            final List<CardView> cardViews = holder.cardViews;

            for (int j = 0; j < deviceTypes.size(); j++) {

                final DeviceType deviceType = deviceTypes.get(j);
                final CardView cardView = cardViews.get(j);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SelectSmartDeviceDialog dialog = new SelectSmartDeviceDialog(getContext(), deviceType, placeGroup);
                        dialog.show();
                    }
                });

                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ConfirmEnableDeviceGroupDialog dialog = new ConfirmEnableDeviceGroupDialog(getContext(), false) {
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

        }

        @Override
        public int getItemCount() {
            return deviceGroupLineDisplayList.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private List<TextView> names;
            private List<ImageView> images;
            private List<CardView> cardViews;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                names = new ArrayList<>();
                images = new ArrayList<>();
                cardViews = new ArrayList<>();

                CardView cardView1 = itemView.findViewById(R.id.card_view_smart_device_group_line_item1);
                CardView cardView2 = itemView.findViewById(R.id.card_view_smart_device_group_line_item2);

                cardView1.setVisibility(View.INVISIBLE);
                cardView2.setVisibility(View.INVISIBLE);

                cardViews.add(cardView1);
                cardViews.add(cardView2);

                for (int i = 0; i < DISPLAY_LINE_CAPACITY; i++) {
                    CardView cardView = cardViews.get(i);

                    ImageView imageView = cardView.findViewById(R.id.smart_device_group_image);
                    TextView textView = cardView.findViewById(R.id.smart_device_group_name);

                    names.add(textView);
                    images.add(imageView);
                }

            }

            private void bind(final DeviceGroupLineDisplay deviceGroupLineDisplay) {

                List<DeviceType> deviceTypes = deviceGroupLineDisplay.getDeviceTypes();

                for (int i = 0; i < deviceTypes.size(); i++) {
                    DeviceType deviceType = deviceTypes.get(i);

                    CardView cardView = cardViews.get(i);
                    cardView.setVisibility(View.VISIBLE);

                    TextView textView = names.get(i);
                    textView.setText(deviceType.getDeviceName());
                    textView.setSelected(true);

                    ImageView imageView = images.get(i);
                    imageView.setImageResource(deviceType.getImageResourceID());
                }

            }
        }
    }
}