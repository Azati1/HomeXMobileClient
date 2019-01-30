package com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bsaldevs.mobileclient.ConditionerWheelAdapter;
import com.bsaldevs.mobileclient.MenuItemData;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class ConditionerSettingsActivity extends AppCompatActivity {

    private CursorWheelLayout conditionerController;
    private List<MenuItemData> menuItemDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditioner_settings);

        conditionerController = findViewById(R.id.conditioner_controller);
        conditionerController.setOnMenuSelectedListener(new CursorWheelLayout.OnMenuSelectedListener() {
            @Override
            public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
                Toast.makeText(getBaseContext(), "Выбран режим: " + menuItemDataList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        menuItemDataList = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        for (int i = 1; i < 9; i++) {
            menuItemDataList.add(new MenuItemData(i + ""));
        }
        ConditionerWheelAdapter adapter = new ConditionerWheelAdapter(getBaseContext(), menuItemDataList);
        conditionerController.setAdapter(adapter);
    }
}
