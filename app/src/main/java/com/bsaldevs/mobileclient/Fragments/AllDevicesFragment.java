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

import com.bsaldevs.mobileclient.Devices.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

public class AllDevicesFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerDevices;
    private List<SmartDeviceInsidePlaceGroupDisplay> smartDeviceInsidePlaceGroupDisplays;
    private MyApplication application;

    private static final int DISPLAY_LINE_CAPACITY = 3;

    private OnFragmentInteractionListener mListener;

    public AllDevicesFragment() {

    }

    public static AllDevicesFragment newInstance() {
        AllDevicesFragment fragment = new AllDevicesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication) getContext().getApplicationContext();
        smartDeviceInsidePlaceGroupDisplays = loadPlaceGroupData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_devices, container, false);
        recyclerDevices = view.findViewById(R.id.recycler_smart_device_place_groups);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

        recyclerDevices.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerDevices.setLayoutManager(horizontalLayoutManager);
        recyclerDevices.setAdapter(new Adapter());
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private List<SmartDeviceInsidePlaceGroupDisplay> loadPlaceGroupData() {
        List<SmartDeviceInsidePlaceGroupDisplay> smartDeviceInsidePlaceGroupDisplays = new ArrayList<>();
        List<PlaceGroup> placeGroups = application.getPlaceGroups();

        for (PlaceGroup placeGroup : placeGroups) {
            SmartDeviceInsidePlaceGroupDisplay smartDeviceInsidePlaceGroupDisplay = new SmartDeviceInsidePlaceGroupDisplay(placeGroup);
            smartDeviceInsidePlaceGroupDisplay.setSmartDeviceLineDisplays(loadDeviceData(placeGroup));
            smartDeviceInsidePlaceGroupDisplays.add(smartDeviceInsidePlaceGroupDisplay);
        }

        return smartDeviceInsidePlaceGroupDisplays;
    }

    private List<SmartDeviceLineDisplay> loadDeviceData(PlaceGroup placeGroup) {

        List<SmartDeviceLineDisplay> smartDeviceLineDisplayList = new ArrayList<>();
        List<SmartDevice> smartDevices = placeGroup.getDevicesInside();

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

    private class SmartDeviceInsidePlaceGroupDisplay {
        private PlaceGroup placeGroup;
        private List<SmartDeviceLineDisplay> smartDeviceLineDisplays;

        public SmartDeviceInsidePlaceGroupDisplay(PlaceGroup placeGroup) {
            this.placeGroup = placeGroup;
            this.smartDeviceLineDisplays = new ArrayList<>();
        }

        public void setSmartDeviceLineDisplays(List<SmartDeviceLineDisplay> smartDeviceLineDisplays) {
            this.smartDeviceLineDisplays = smartDeviceLineDisplays;
        }

        public List<SmartDeviceLineDisplay> getSmartDeviceLineDisplays() {
            return smartDeviceLineDisplays;
        }

        public PlaceGroup getPlaceGroup() {
            return placeGroup;
        }
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
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_smart_device_group_inside_place_group, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {

            final SmartDeviceInsidePlaceGroupDisplay smartDeviceInsidePlaceGroupDisplay = smartDeviceInsidePlaceGroupDisplays.get(i);
            final List<SmartDeviceLineDisplay> smartDeviceLineDisplay = smartDeviceInsidePlaceGroupDisplay.smartDeviceLineDisplays;
            final PlaceGroup placeGroups = smartDeviceInsidePlaceGroupDisplay.getPlaceGroup();
            final List<SmartDevice> smartDevices = placeGroups.getDevicesInside();

            holder.bind(smartDeviceInsidePlaceGroupDisplay);
        }

        @Override
        public int getItemCount() {
            return smartDeviceInsidePlaceGroupDisplays.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView placeGroupName;
            private RecyclerView recyclerView;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                placeGroupName = itemView.findViewById(R.id.place_group_name);
                recyclerView = itemView.findViewById(R.id.recycler_smart_devices);
            }

            private void bind(final SmartDeviceInsidePlaceGroupDisplay smartDeviceInsidePlaceGroupDisplay) {

                PlaceGroup placeGroup = smartDeviceInsidePlaceGroupDisplay.getPlaceGroup();
                placeGroupName.setText(placeGroup.getName());
                int resId = R.anim.layout_animation_fall_down;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

                recyclerView.setLayoutAnimation(animation);
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManager);
                recyclerView.setAdapter(new InnerAdapter(smartDeviceInsidePlaceGroupDisplay.getSmartDeviceLineDisplays()));

            }

        }

    }

    private class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.ItemViewHolder> {

        private List<SmartDeviceLineDisplay> smartDeviceLineDisplays;

        public InnerAdapter(List<SmartDeviceLineDisplay> smartDeviceLineDisplays) {
            this.smartDeviceLineDisplays = smartDeviceLineDisplays;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_devices_line_view, viewGroup, false);
            return new InnerAdapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int i) {

            final SmartDeviceLineDisplay smartDeviceLineDisplay = smartDeviceLineDisplays.get(i);
            final List<SmartDevice> smartDevices = smartDeviceLineDisplay.getSmartDevices();

            holder.bind(smartDeviceLineDisplay);

            final List<CardView> cardViews = holder.cardViews;

            for (int j = 0; j < smartDevices.size(); j++) {

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
            return smartDeviceLineDisplays.size();
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

            }

        }
    }

}
