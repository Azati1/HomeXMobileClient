package com.bsaldevs.mobileclient.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bsaldevs.mobileclient.R;

public class ChangePlaceGroupDataDialog extends Dialog {

    private EditText editText;
    private ImageButton imageButton;

    private String currentName;
    private int currentImageRes;

    public ChangePlaceGroupDataDialog(@NonNull Context context, String currentName, int currentImageRes) {
        super(context);
        this.currentName = currentName;
        this.currentImageRes = currentImageRes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_place_group_data);
        editText = findViewById(R.id.edit_place_group_name);
        imageButton = findViewById(R.id.edit_place_group_image);

        editText.setText(currentName);
        imageButton.setImageResource(currentImageRes);
        imageButton.setTag(currentImageRes);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Меняем пикчу", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
