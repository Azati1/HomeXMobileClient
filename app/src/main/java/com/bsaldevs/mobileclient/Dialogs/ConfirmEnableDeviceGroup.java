package com.bsaldevs.mobileclient.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bsaldevs.mobileclient.R;

public abstract class ConfirmEnableDeviceGroup extends Dialog {

    private TextView title;
    private Button confirm;
    private boolean deviceGroupEnabled;
    private static final String enabledTitleText = "Вы уверены, что хотите выключить устройства?";
    private static final String disabledTitleText = "Вы уверены, что хотите включить устройства?";
    private static final String enabledButtonText = "Выключить";
    private static final String disabledButtonText = "Включить";

    public ConfirmEnableDeviceGroup(@NonNull Context context, boolean deviceGroupEnabled) {
        super(context);
        this.deviceGroupEnabled = deviceGroupEnabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm_enable_device_group);
        title = findViewById(R.id.text_confirm_enable_device_group);
        confirm = findViewById(R.id.button_confirm_enable_device_group);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnConfirmListener(deviceGroupEnabled);
            }
        });
        putData();
    }

    private void putData() {
        if (deviceGroupEnabled) {
            title.setText(enabledTitleText);
            confirm.setText(enabledButtonText);
        }
        else {
            title.setText(disabledTitleText);
            confirm.setText(disabledButtonText);
        }
    }

    public abstract void setOnConfirmListener(boolean deviceGroupEnabled);
}
