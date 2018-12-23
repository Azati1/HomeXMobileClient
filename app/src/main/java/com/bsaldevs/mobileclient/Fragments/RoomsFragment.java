package com.bsaldevs.mobileclient.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsaldevs.mobileclient.Activities.DeviceGroupActivity;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

public class RoomsFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;
    private List<PlaceGroup> places;

    public RoomsFragment() {
        // Required empty public constructor
    }

    public static RoomsFragment newInstance() {
        RoomsFragment fragment = new RoomsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        places = new ArrayList<>();
        places.add(new PlaceGroup("Офис", R.drawable.ic_desk));
        places.add(new PlaceGroup("Гостиная", R.drawable.ic_tv));
        places.add(new PlaceGroup("Ванная", R.drawable.ic_bathtub));
        places.add(new PlaceGroup("Спальня", R.drawable.ic_bunk_bed));
        places.add(new PlaceGroup("Уборная", R.drawable.ic_wc));
        places.add(new PlaceGroup("Кухня", R.drawable.ic_stove));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.place_group_recycler);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);

        recyclerView.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(new Adapter());

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

    private class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

        @NonNull
        @Override
        public Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_place_group_item, viewGroup, false);
            return new Adapter.ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final Adapter.ItemViewHolder holder, final int i) {

            final PlaceGroup placeGroup = places.get(i);

            holder.bind(placeGroup);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DeviceGroupActivity.class);
                    intent.putExtra("placeGroupName", placeGroup.getName());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return places.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private ImageView image;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.place_group_name);
                image = itemView.findViewById(R.id.place_group_image);
            }

            private void bind(final PlaceGroup item) {
                name.setText(item.getName());
                image.setImageResource(item.getImageResourceID());
            }
        }
    }
}
