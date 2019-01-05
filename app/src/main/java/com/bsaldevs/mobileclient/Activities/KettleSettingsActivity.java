package com.bsaldevs.mobileclient.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.bsaldevs.mobileclient.ConditionerWheelAdapter;
import com.bsaldevs.mobileclient.MenuItemData;
import com.bsaldevs.mobileclient.R;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class KettleSettingsActivity extends Activity {

    private CursorWheelLayout kettleController;
    private List<MenuItemData> menuItemDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_kettle_settings);

        kettleController = findViewById(R.id.conditioner_controller2);
        kettleController.setOnMenuSelectedListener(new CursorWheelLayout.OnMenuSelectedListener() {
            @Override
            public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
                Toast.makeText(getBaseContext(), "Температура подогрева: " + menuItemDataList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        menuItemDataList = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        for (int i = 1; i <= 9; i++) {
            menuItemDataList.add(new MenuItemData(i*10 + "°"));
        }
        ConditionerWheelAdapter adapter = new ConditionerWheelAdapter(getBaseContext(), menuItemDataList);
        kettleController.setAdapter(adapter);
    }

}
