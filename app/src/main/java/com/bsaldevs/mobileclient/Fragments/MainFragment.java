package com.bsaldevs.mobileclient.Fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsaldevs.mobileclient.Activities.LampSettingsActivity;
import com.bsaldevs.mobileclient.Devices.Component;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.DeviceGroup;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends android.support.v4.app.Fragment {

    private List<RecyclerItem> items;
    private List<Component> components;
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication application = (MyApplication) (getActivity().getApplication());
        components = application.getComponents();
        items = setupItems();
    }

    private List<RecyclerItem> setupItems() {
        List<RecyclerItem> items = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof DeviceGroup)
                items.add(new GroupItem(component));
            else
                items.add(new Item(component));
        }
        return items;
    }

    private interface RecyclerItem {
        void setExpanded(boolean expanded);
        boolean isExpanded();
        String getName();
        List<Component> getComponents();
        Component getComponent();
    }

    private class Item implements RecyclerItem {
        private Component component;
        boolean expanded = false;

        public Item(Component component) {
            this.component = component;
        }

        @Override
        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        @Override
        public boolean isExpanded() {
            return expanded;
        }

        @Override
        public String getName() {
            return component.getName();
        }

        @Override
        public List<Component> getComponents() {
            return new ArrayList<>();
        }

        @Override
        public Component getComponent() {
            return component;
        }

    }

    private class GroupItem implements RecyclerItem {

        private Component component;
        private boolean expanded = false;

        public GroupItem(Component component) {
            this.component = component;
        }

        public List<Component> getComponents() {
            return ((DeviceGroup) (component)).getComponents();
        }

        @Override
        public String getName() {
            return component.getName();
        }

        @Override
        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        @Override
        public boolean isExpanded() {
            return expanded;
        }

        @Override
        public Component getComponent() {
            return component;
        }

    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_smart_device_item, viewGroup, false);
            return new ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int i) {

            final RecyclerItem item = items.get(i);

            holder.bind(item);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharedIntent = new Intent(getContext(), LampSettingsActivity.class);

                    Pair[] pairs = new Pair[1];

                    pairs[0] = new Pair<View, String>(holder.name, "shared_name");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);

                    startActivity(sharedIntent, options.toBundle());
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private ImageView image;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                /*name = itemView.findViewById(R.id.textView7);
                image = itemView.findViewById(R.id.imageView6);*/
            }

            private void bind(final RecyclerItem item) {
                name.setText(item.getName());
                image.setImageResource(item.getComponent().getImageResourceID());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.recycler_groups_of_devices);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);

        recyclerView.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(new Adapter());

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
