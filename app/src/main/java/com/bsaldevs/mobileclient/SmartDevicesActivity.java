package com.bsaldevs.mobileclient;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

public class SmartDevicesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_devices);
        LinearLayout linearLayout = findViewById(R.id.lamp_container_linear_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectSmartDeviceDialog dialog = new SelectSmartDeviceDialog(SmartDevicesActivity.this);
                dialog.show();
            }
        });
    }
}
