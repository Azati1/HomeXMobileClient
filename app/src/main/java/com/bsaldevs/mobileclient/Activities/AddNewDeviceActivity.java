package com.bsaldevs.mobileclient.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

public class AddNewDeviceActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private EditText editText;
    private Button buttonCancel;
    private Button buttonConfirm;

    private List<RecyclerItem> recyclerItems;
    //private List<RadioButton> radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.dialog_add_new_devices);

        searchView = findViewById(R.id.search_device_view);
        buttonCancel = findViewById(R.id.button_cancel_adding);
        buttonConfirm = findViewById(R.id.button_confirm_adding);
        recyclerView = findViewById(R.id.recycler_view_all_device_type);
        editText = findViewById(R.id.edit_new_device_name);

        //radioButtons = new ArrayList<>();
        recyclerItems = new ArrayList<>();
        loadRecyclerItems();

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(AddNewDeviceActivity.this, resId);

        recyclerView.setLayoutAnimation(animation);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(AddNewDeviceActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(new Adapter());

    }

    private void loadRecyclerItems() {
        recyclerItems.add(new RecyclerItem("Лампочка", R.drawable.lamp_on));
        recyclerItems.add(new RecyclerItem("Розетка", R.drawable.ic_socket));
        recyclerItems.add(new RecyclerItem("Замок", R.drawable.ic_lock));
        recyclerItems.add(new RecyclerItem("Кондиционер", R.drawable.ic_air_conditioner));
        recyclerItems.add(new RecyclerItem("Обогрев", R.drawable.ic_thermometer));
        recyclerItems.add(new RecyclerItem("Окружение", R.drawable.ic_sound_system));
        recyclerItems.add(new RecyclerItem("Пылесос", R.drawable.ic_hoover));
        recyclerItems.add(new RecyclerItem("Шторы", R.drawable.ic_window));
        recyclerItems.add(new RecyclerItem("Камера", R.drawable.ic_camera));
        recyclerItems.add(new RecyclerItem("Чайник", R.drawable.ic_kettle));
    }

    private class RecyclerItem {

        private String name;
        private int imageResId;
        private boolean selected;

        public RecyclerItem(String name, int imageResId) {
            this.name = name;
            this.imageResId = imageResId;
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
            private RadioButton radioButton;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.card_device_type_image);
                textView = itemView.findViewById(R.id.card_device_type_text);
                radioButton = itemView.findViewById(R.id.card_device_radio_button);

                //radioButtons.add(radioButton);

            }

            private void bind(final RecyclerItem recyclerItem) {

                imageView.setImageResource(recyclerItem.imageResId);
                textView.setText(recyclerItem.name);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        for (int i = 0; i < recyclerItems.size(); i++) {
                            recyclerItems.get(i).selected = false;
                            //radioButtons.get(i).setActivated(false);
                        }

                        recyclerItem.selected = true;
                        radioButton.setActivated(true);
                    }
                });

            }

        }
    }
}
