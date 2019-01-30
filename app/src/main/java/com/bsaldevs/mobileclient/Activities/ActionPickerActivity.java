package com.bsaldevs.mobileclient.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bsaldevs.mobileclient.R;

public class ActionPickerActivity extends AppCompatActivity {

    private RecyclerView recyclerActionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_picker);
    }
}
