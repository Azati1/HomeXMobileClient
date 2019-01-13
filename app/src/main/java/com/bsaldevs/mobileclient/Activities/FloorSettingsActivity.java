package com.bsaldevs.mobileclient.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bsaldevs.mobileclient.ConditionerWheelAdapter;
import com.bsaldevs.mobileclient.MenuItemData;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class FloorSettingsActivity extends AppCompatActivity {

    private CursorWheelLayout Controller;
    private List<MenuItemData> menuItemDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_floor_settings);
        getSupportActionBar().hide();

        Controller = findViewById(R.id.conditioner_controller4);
        Controller.setOnMenuSelectedListener(new CursorWheelLayout.OnMenuSelectedListener() {
            @Override
            public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
                Toast.makeText(getBaseContext(), "Температура подогрева: " + menuItemDataList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        menuItemDataList = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        for (int i = 0; i < 9; i++) {
            menuItemDataList.add(new MenuItemData(i*5 + "°"));
        }
        ConditionerWheelAdapter adapter = new ConditionerWheelAdapter(getBaseContext(), menuItemDataList);
        Controller.setAdapter(adapter);
    }

}
