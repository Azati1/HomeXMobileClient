package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Fragments.RegistrationFragment;
import com.bsaldevs.mobileclient.User.Account;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (MyApplication) getApplication();

        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        FragmentManager manager = getSupportFragmentManager();
        RegistrationFragment registrationFragment = new RegistrationFragment();
        manager.beginTransaction().replace(R.id.bottom_registration_sheet, registrationFragment).commit();

        TextView title = findViewById(R.id.textView10);
        EditText editLogin = findViewById(R.id.editText2);
        EditText editPassword = findViewById(R.id.editText4);
        Button login = findViewById(R.id.button9);
        //TextView titleLoginBy = findViewById(R.id.textView9);
        final ImageButton loginByFacebookButton = findViewById(R.id.imageButtonFacebook);
        final ImageButton loginByGooglePlusButton = findViewById(R.id.imageButtonGoogle);
        final ImageButton loginByVKButton = findViewById(R.id.imageButtonVK);
        ImageButton about = findViewById(R.id.about);
        Button soc = findViewById(R.id.soc_button);
        final TextView textsoc = findViewById(R.id.soc_text);

        View sheet = findViewById(R.id.bottom_registration_sheet);

        final ImageView slideArrow = sheet.findViewById(R.id.image_slide_sheet_arrow);

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(sheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Log.d("CDA", "bottom sheet onStateChanged");
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                Log.d("CDA", "bottom sheet onSlide");
            }
        });

        final Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_slide_arrow);

        Button button = findViewById(R.id.button10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideArrow.startAnimation(rotation);
            }
        });

        sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("CDA", bottomSheetBehavior.getState() + "");

                switch (bottomSheetBehavior.getState()) {
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                        /*RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                        rotateAnimation.setFillAfter(true);
                        rotateAnimation.setDuration(1000);
                        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                        slideArrow.startAnimation(rotateAnimation);*/

                        //slideArrow.startAnimation(rotation);

                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutOfProgram = new Intent(LoginActivity.this, AboutOfProgramActivity.class);
                startActivity(aboutOfProgram);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(login);
            }
        });

        loginByFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by facebook", Toast.LENGTH_SHORT).show();
            }
        });

        loginByGooglePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by google plus", Toast.LENGTH_SHORT).show();
            }
        });

        loginByVKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by vk", Toast.LENGTH_SHORT).show();
                VKSdk.login(LoginActivity.this);
            }
        });

        loginByVKButton.setVisibility(loginByVKButton.INVISIBLE);
        loginByGooglePlusButton.setVisibility(loginByGooglePlusButton.INVISIBLE);
        loginByFacebookButton.setVisibility(loginByFacebookButton.INVISIBLE);

        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        final Animation animScaleMin = AnimationUtils.loadAnimation(this, R.anim.scale_to_min);

        soc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScaleMin);
                view.setVisibility(view.INVISIBLE);
                textsoc.setVisibility(textsoc.VISIBLE);
                textsoc.startAnimation(animScale);

                loginByVKButton.setVisibility(loginByVKButton.VISIBLE);
                loginByVKButton.startAnimation(animScale);
                loginByGooglePlusButton.setVisibility(loginByGooglePlusButton.VISIBLE);
                loginByGooglePlusButton.startAnimation(animScale);
                loginByFacebookButton.setVisibility(loginByFacebookButton.VISIBLE);
                loginByFacebookButton.startAnimation(animScale);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                VKRequest request = VKApi.users().get();
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        String status = "";

                        try {

                            JSONArray jsonArray = response.json.getJSONArray("response");

                            for (int i = 0; i < 1; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String first_name = jsonObject.getString("first_name");
                                String last_name = jsonObject.getString("last_name");
                                Log.d("CDA", first_name + " " + last_name);// Пользователь успешно авторизовался
                                login(new Account(first_name, last_name));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void login(Account account) {
        application.login(account);
        Intent login = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(login);
    }
}