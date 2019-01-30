package com.bsaldevs.mobileclient.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.bsaldevs.mobileclient.CustomViews.CircleStatusBar;
import com.bsaldevs.mobileclient.R;

public class ActionScheduleActivity extends AppCompatActivity {

    private LinearLayout linearLayoutActionWhen;
    private LinearLayout linearLayoutActionThen;

    private CircleStatusBar circleStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_schedule);
        linearLayoutActionWhen = findViewById(R.id.linear_layout_action_when);
        linearLayoutActionThen = findViewById(R.id.linear_layout_action_then);

        linearLayoutActionWhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActionEventPicker();
            }
        });

        linearLayoutActionThen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActionResultPicker();
            }
        });

        circleStatusBar = findViewById(R.id.circle_progress_bar);
    }

    private void showActionEventPicker() {
        circleStatusBar.setValue(75);
    }

    private void showActionResultPicker() {
        circleStatusBar.setValue(20);
    }
}
