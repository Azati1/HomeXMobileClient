package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.Net.Response;
import com.bsaldevs.mobileclient.Net.ServerCallback;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Fragments.RegistrationFragment;
import com.bsaldevs.mobileclient.User.Account;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    private MyApplication application;

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegistration;

    private CallbackManager callbackManager;
    private LoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (MyApplication) getApplication();

        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        FragmentManager manager = getSupportFragmentManager();
        RegistrationFragment registrationFragment = new RegistrationFragment();
        manager.beginTransaction().replace(R.id.bottom_registration_sheet, registrationFragment).commit();

        final EditText editLogin = findViewById(R.id.editText2);
        final EditText editPassword = findViewById(R.id.editText4);
        Button loginByApplication = findViewById(R.id.button9);
        //TextView titleLoginBy = findViewById(R.id.textView9);
        final ImageButton loginByFacebookButton = findViewById(R.id.imageButtonFacebook);
        final ImageButton loginByGooglePlusButton = findViewById(R.id.imageButtonGoogle);
        final ImageButton loginByVKButton = findViewById(R.id.imageButtonVK);

        final TextView textsoc = findViewById(R.id.soc_text);

        View sheet = findViewById(R.id.bottom_registration_sheet);

        editTextName = sheet.findViewById(R.id.edit_text_name);
        editTextEmail = sheet.findViewById(R.id.edit_text_email);
        editTextPassword = sheet.findViewById(R.id.edit_text_password);
        buttonRegistration = sheet.findViewById(R.id.button_registration);


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

        LinearLayout linearLayout = sheet.findViewById(R.id.bottom_sheet_head);

        final ImageView slideArrow = linearLayout.findViewById(R.id.image_slide_sheet_arrow);
        final Animation rotation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.rotate_slide_arrow);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CDA", bottomSheetBehavior.getState() + "");

                slideArrow.startAnimation(rotation);

                switch (bottomSheetBehavior.getState()) {
                    case BottomSheetBehavior.STATE_COLLAPSED: {

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

        loginByApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editLogin.getText().toString();
                String password = editPassword.getText().toString();

                login(email, password);
            }
        });

       /* loginByFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by facebook", Toast.LENGTH_SHORT).show();
            }
        });*/


        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginFB();

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

    }




    private void loginFB(){
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                        GraphRequest request=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String first_name = object.getString("first_name");
                                    String url_photo = "https://graph.facebook.com/"+object.getString("id")+"/picture?width=50&height=50";
                                    Log.d("CDA", first_name );
                                    Account account = new Account();
                                    account.setName(first_name);
                                    account.setUrlPhoto(url_photo);
                                    application.setAccount(account);
                                    //login(account);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields","id,first_name,last_name");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LoginActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_50"));
                //VKRequest request = VKApi.users().get();
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
                                String url_photo = jsonObject.getString("photo_50");
                                Log.d("CDA", first_name);// Пользователь успешно авторизовался
                                Account account = new Account();
                                account.setName(first_name);
                                account.setUrlPhoto(url_photo);
                                application.setAccount(account);
                                //login(account);
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
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void login(String email, String password) {

        final Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);

        String[] args = new String[2];
        args[0] = email;
        args[1] = password;

        RequestPoll requestPoll = application.getRequestPoll();
        Request request = new Request("client", "server", "login", args);
        request.executeWithListener(new ServerCallback() {
            @Override
            public void onComplete(Response response) {

                if (response.getFuncName().equals("login")) {
                    String[] args = response.getFuncArgs();
                    if (args[0].equals("ok")) {
                        String name = args[1];
                        account.setName(name);
                        application.setAccount(account);
                        Intent login = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(login);
                    }
                    if (args[0].equals("error")) {
                        ShowToast showToast = new ShowToast("Не могу войти");
                        showToast.execute();
                    }
                }
            }
        });
        requestPoll.execute(request);

    }

    private class ShowToast extends AsyncTask<Void, Void, Void> {

        private String value;

        public ShowToast(String value) {
            this.value = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(LoginActivity.this, value, Toast.LENGTH_SHORT).show();
        }
    }
}