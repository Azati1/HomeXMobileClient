package com.bsaldevs.mobileclient.Activities;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bsaldevs.mobileclient.R;

public class LampSettingsActivity extends AppCompatActivity {

    private Button showColorPicker;
    private GestureDetector detector;

    private GestureAdapter adapter;
    private ImageView colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_settings);
        showColorPicker = findViewById(R.id.button4);
        detector = new GestureDetector(LampSettingsActivity.this, new MyGestureDetector());

        showColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LampSettingsActivity.this);
                View picker = getLayoutInflater().inflate(R.layout.color_picker, null);
                colorPicker = picker.findViewById(R.id.imageView5);
                colorPicker.setOnTouchListener(touchListener);
                builder.setView(picker);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return detector.onTouchEvent(motionEvent);
        }
    };

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        public MyGestureDetector() {
            adapter = new GestureAdapter();
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

            adapter.onChanged(motionEvent, motionEvent1);
            colorPicker.setColorFilter(Color.argb(adapter.a, adapter.r, adapter.g, adapter.b));

            if (motionEvent.getX() < motionEvent1.getX()) {
                Log.d("CDA", "right");
            }
            if (motionEvent.getX() > motionEvent1.getX()) {
                Log.d("CDA", "left");
            }
            if (motionEvent.getY() > motionEvent1.getY()) {
                Log.d("CDA", "up");
            }
            if (motionEvent.getY() < motionEvent1.getY()) {
                Log.d("CDA", "down");
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }

    private class GestureAdapter implements GestureListener {

        private int a;
        private int r;
        private int g;
        private int b = 0;

        @Override
        public void onChanged(MotionEvent motionEvent, MotionEvent motionEvent1) {

            /*Log.d("CDA", "x = " + motionEvent.getX());
            Log.d("CDA", "y = " + motionEvent.getY());
            Log.d("CDA", "x1 = " + motionEvent1.getX());
            Log.d("CDA", "y1 = " + motionEvent1.getY());*/

            Log.d("CDA", "dif x = " + (motionEvent.getX() - motionEvent1.getX()));
            //Log.d("CDA", "dif y = " + (motionEvent.getY() - motionEvent1.getY()));

            int xStep = (int) (motionEvent.getX() - motionEvent1.getX()) / 8;
            int yStep = (int) (motionEvent.getY() - motionEvent1.getY());



            //g = Math.abs (g - (255 - (xStep % 255)));
            Log.d("CDA", "g = " + g);

            if (g + xStep > 255) {
                g = 255 - (xStep - (255 - g));
            } else if (g + xStep < 0) {
                g = Math.abs(xStep) - g;
            } else {
                g += xStep;
            }

            Log.d("CDA", "xStep = " + xStep);
            Log.d("CDA", "g = " + g);

            a = 255;
            r = 0;
            //g += greenStep;
            //b = (int) Math.abs(motionEvent.getY() - motionEvent1.getY() / 8);
        }

    }

    interface GestureListener {
        void onChanged(MotionEvent motionEvent, MotionEvent motionEvent1);
    }
}
