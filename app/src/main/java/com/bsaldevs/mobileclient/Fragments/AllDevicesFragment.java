package com.bsaldevs.mobileclient.Fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.futuremind.recyclerviewfastscroll.viewprovider.DefaultBubbleBehavior;
import com.futuremind.recyclerviewfastscroll.viewprovider.ScrollerViewProvider;
import com.futuremind.recyclerviewfastscroll.viewprovider.ViewBehavior;
import com.futuremind.recyclerviewfastscroll.viewprovider.VisibilityAnimationManager;

import java.util.ArrayList;
import java.util.List;

public class AllDevicesFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerSmartDevicesPlageGroup;
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
        recyclerSmartDevicesPlageGroup = view.findViewById(R.id.recycler_smart_device_place_groups);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

        recyclerSmartDevicesPlageGroup.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerSmartDevicesPlageGroup.setLayoutManager(horizontalLayoutManager);
        recyclerSmartDevicesPlageGroup.setAdapter(new Adapter());

        FastScroller fastScroller = view.findViewById(R.id.fast_scroll);
        fastScroller.setRecyclerView(recyclerSmartDevicesPlageGroup); // only after recyclerView.serAdapter()
        fastScroller.setViewProvider(new CustomScrollerViewProvider());

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

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> implements SectionTitleProvider {

        @NonNull
        @Override
        public Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_smart_device_group_inside_place_group, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {
            final SmartDeviceInsidePlaceGroupDisplay smartDeviceInsidePlaceGroupDisplay = smartDeviceInsidePlaceGroupDisplays.get(i);
            holder.bind(smartDeviceInsidePlaceGroupDisplay);
        }

        @Override
        public int getItemCount() {
            return smartDeviceInsidePlaceGroupDisplays.size();
        }

        @Override
        public String getSectionTitle(int position) {
            return String.valueOf(smartDeviceInsidePlaceGroupDisplays.get(position).getPlaceGroup().getName().charAt(0));
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

    public class CustomScrollerViewProvider extends ScrollerViewProvider {

        private TextView bubble;
        private View handle;

        @Override
        public View provideHandleView(ViewGroup container) {
            handle = new View(getContext());
            handle.setLayoutParams(new ViewGroup.LayoutParams(15, 60));
            handle.setBackgroundResource(R.drawable.rounded_corners_rectangle_slider);
            handle.setVisibility(View.INVISIBLE);
            return handle;
        }

        @Override
        public View provideBubbleView(ViewGroup container) {
            bubble = new TextView(getContext());
            bubble.setTextSize(30);
            bubble.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            bubble.setBackgroundResource(R.drawable.rounded_corners_rectangle_bubble_slider);
            bubble.setVisibility(View.INVISIBLE);
            bubble.setGravity(Gravity.CENTER);
            bubble.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            /*getScroller().addScrollerListener(new RecyclerViewScrollListener.ScrollerListener() {
                @Override
                public void onScroll(float relativePos) {
                    //Yeah, yeah, but we were so preoccupied with whether or not we could,
                    //that we didn't stop to think if we should.
                    bubble.setRotation(relativePos*360f);
                }
            });*/
            return bubble;
        }

        @Override
        public TextView provideBubbleTextView() {
            return bubble;
        }

        @Override
        public int getBubbleOffset() {
            return (int) (getScroller().isVertical() ? (float)handle.getHeight()/2f-(float)bubble.getHeight()/2f : (float)handle.getWidth()/2f-(float)bubble.getWidth()/2);
        }

        @Override
        protected ViewBehavior provideHandleBehavior() {
            return new CustomHandleBehavior(
                    new VisibilityAnimationManager.Builder(handle)
                            .withHideDelay(2000)
                            .build(),
                    new CustomHandleBehavior.HandleAnimationManager.Builder(handle)
                            .withGrabAnimator(R.animator.custom_grab)
                            .withReleaseAnimator(R.animator.custom_release)
                            .build()
            );
        }

        @Override
        protected ViewBehavior provideBubbleBehavior() {
            return new DefaultBubbleBehavior(new VisibilityAnimationManager.Builder(bubble).withHideDelay(0).build());
        }

        private ShapeDrawable drawRect (int width, int height, int color) {
            ShapeDrawable rect = new ShapeDrawable (new RectShape());
            rect.setIntrinsicHeight(height);
            rect.setIntrinsicWidth(width);
            rect.getPaint().setColor(color);
            return rect;
        }

    }

    public static class CustomHandleBehavior implements ViewBehavior{

        private final VisibilityAnimationManager visibilityManager;
        private final HandleAnimationManager grabManager;

        private boolean isGrabbed;

        public CustomHandleBehavior(VisibilityAnimationManager visibilityManager, HandleAnimationManager grabManager) {
            this.visibilityManager = visibilityManager;
            this.grabManager = grabManager;
        }

        @Override
        public void onHandleGrabbed() {
            isGrabbed = true;
            visibilityManager.show();
            grabManager.onGrab();
        }

        @Override
        public void onHandleReleased() {
            isGrabbed = false;
            visibilityManager.hide();
            grabManager.onRelease();
        }

        @Override
        public void onScrollStarted() {
            visibilityManager.show();
        }

        @Override
        public void onScrollFinished() {
            if(!isGrabbed) visibilityManager.hide();
        }

        static class HandleAnimationManager {

            @Nullable
            private AnimatorSet grabAnimator;
            @Nullable
            private AnimatorSet releaseAnimator;

            protected HandleAnimationManager(View handle, @AnimatorRes int grabAnimator, @AnimatorRes int releaseAnimator) {
                if (grabAnimator != -1) {
                    this.grabAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(handle.getContext(), grabAnimator);
                    this.grabAnimator.setTarget(handle);
                }
                if (releaseAnimator != -1) {
                    this.releaseAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(handle.getContext(), releaseAnimator);
                    this.releaseAnimator.setTarget(handle);
                }
            }

            public void onGrab() {
                if (releaseAnimator != null) {
                    releaseAnimator.cancel();
                }
                if (grabAnimator != null) {
                    grabAnimator.start();
                }
            }

            public void onRelease() {
                if (grabAnimator != null) {
                    grabAnimator.cancel();
                }
                if (releaseAnimator != null) {
                    releaseAnimator.start();
                }
            }

            public static class Builder {
                private View handle;
                private int grabAnimator;
                private int releaseAnimator;

                public Builder(View handle) {
                    this.handle = handle;
                }

                public Builder withGrabAnimator(@AnimatorRes int grabAnimator) {
                    this.grabAnimator = grabAnimator;
                    return this;
                }

                public Builder withReleaseAnimator(@AnimatorRes int releaseAnimator) {
                    this.releaseAnimator = releaseAnimator;
                    return this;
                }

                public HandleAnimationManager build() {
                    return new HandleAnimationManager(handle, grabAnimator, releaseAnimator);
                }
            }
        }

    }

}
