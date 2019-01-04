package com.bsaldevs.mobileclient.Fragments;

import android.content.Context;
import android.content.Intent;
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

import com.bsaldevs.mobileclient.Devices.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Dialogs.ConfirmEnableDeviceGroup;
import com.bsaldevs.mobileclient.Dialogs.SelectSmartDeviceDialog;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

public class DevicesFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerDeviceGroup;
    private PlaceGroup placeGroup;
    private List<SmartDeviceLineDisplay> deviceGroupLineDisplayList;
    private MyApplication application;
    private String placeGroupName;

    private static final int DISPLAY_LINE_CAPACITY = 3;

    public DevicesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DevicesFragment newInstance(String placeGroupName) {
        DevicesFragment fragment = new DevicesFragment();
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

        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        recyclerDeviceGroup = view.findViewById(R.id.recycler_all_devices);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

        recyclerDeviceGroup.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerDeviceGroup.setLayoutManager(horizontalLayoutManager);
        recyclerDeviceGroup.setAdapter(new Adapter());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private List<SmartDeviceLineDisplay> loadDeviceGroupData() {

        List<SmartDeviceLineDisplay> smartDeviceLineDisplayList = new ArrayList<>();
        List<SmartDevice> smartDevices = placeGroup.getDevicesInside();

        for (int i = 0;; i++) {

            if (i * DISPLAY_LINE_CAPACITY > smartDevices.size())
                break;

            SmartDeviceLineDisplay smartDeviceLineDisplay = new SmartDeviceLineDisplay();

            for (int j = 0; j < DISPLAY_LINE_CAPACITY; j++) {

                if (i * DISPLAY_LINE_CAPACITY + j >= smartDevices.size())
                    break;

                SmartDevice smartDevice = smartDevices.get(i * DISPLAY_LINE_CAPACITY + j);
                smartDeviceLineDisplay.addDeviceToLine(smartDevice);
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

        public void addDeviceToLine(SmartDevice smartDevice) {
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
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.devices_line_view, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {

            final SmartDeviceLineDisplay smartDeviceLineDisplay = deviceGroupLineDisplayList.get(i);
            final List<SmartDevice> smartDevices = smartDeviceLineDisplay.getSmartDevices();

            holder.bind(smartDeviceLineDisplay);

            for (int j = 0; j < smartDevices.size(); j++) {

                final List<CardView> cardViews = holder.cardViews;
                final SmartDevice smartDevice = smartDevices.get(j);
                final CardView cardView = cardViews.get(j);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), smartDevice.getDisplayActivity());
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

                    ImageView imageView = imageViewsPicture.get(i);
                    imageView.setImageResource(smartDevice.getImageResourceID());
                }

            }
        }
    }
}
