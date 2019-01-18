package com.bsaldevs.mobileclient.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsaldevs.mobileclient.Devices.DeviceType;
import com.bsaldevs.mobileclient.Devices.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

public class ConfirmAddNewDeviceDialog extends Dialog {

    private TextView textViewDeviceTypeName;
    private ImageView imageViewDeviceType;
    private EditText editDeviceName;
    private ImageButton buttonCancel;
    private Button buttonConfirm;

    private MyApplication application;
    private PlaceGroup placeGroup;
    private DeviceType deviceType;

    public ConfirmAddNewDeviceDialog(@NonNull Context context, PlaceGroup placeGroup, DeviceType deviceType) {
        super(context);
        this.application = ((MyApplication) getContext().getApplicationContext());
        this.placeGroup = placeGroup;
        this.deviceType = deviceType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_add_new_device);

        textViewDeviceTypeName = findViewById(R.id.text_device_type_name);
        imageViewDeviceType = findViewById(R.id.image_device_type);
        editDeviceName = findViewById(R.id.edit_device_name);
        buttonCancel = findViewById(R.id.button_cancel);
        buttonConfirm = findViewById(R.id.button_confirm);

        editDeviceName.requestFocus();

        textViewDeviceTypeName.setText(deviceType.getDeviceName());
        imageViewDeviceType.setImageResource(deviceType.getImageResourceID());

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmartDevice smartDevice = SmartDevice.create(deviceType, editDeviceName.getText().toString(), placeGroup, application.getClient().getConnection());
                    application.addSmartDevice(smartDevice);
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
