package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bsaldevs.mobileclient.User.Mobile;
import com.bsaldevs.mobileclient.User.MobileClient;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.User.UserDevice;

public class FirstConnectionActivity extends AppCompatActivity {

    private EditText editName;
    private Button buttonContinue;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_connection);

        editName = findViewById(R.id.editText);
        buttonContinue = findViewById(R.id.button_forward_to_second_init_step);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserDevice mobile = new Mobile(editName.getText().toString());

                //String ip = "localhost";
                String ip = "192.168.0.101";
                int port = 3346;

                application = (MyApplication) getApplication();
                application.setupClient(new MobileClient(ip, port, mobile));

                Intent intent = new Intent(FirstConnectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}