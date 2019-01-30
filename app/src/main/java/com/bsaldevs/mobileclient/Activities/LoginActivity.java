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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.Net.Response;
import com.bsaldevs.mobileclient.Net.ServerCallback;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Fragments.RegistrationFragment;
import com.bsaldevs.mobileclient.Tasks;
import com.bsaldevs.mobileclient.User.Account;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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

public class LoginActivity extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    private MyApplication application;
    private CallbackManager callbackManager;
    private LoginButton facebookBaseLoginButton;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        application = (MyApplication) getApplication();
        callbackManager = CallbackManager.Factory.create();
        initGUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initGUI() {

        FragmentManager manager = getSupportFragmentManager();
        RegistrationFragment registrationFragment = new RegistrationFragment();
        manager.beginTransaction().replace(R.id.bottom_registration_sheet, registrationFragment).commit();

        final EditText editLogin = findViewById(R.id.edit_login);
        final EditText editPassword = findViewById(R.id.edit_password);

        final Button loginByApplication = findViewById(R.id.button_login_by_application);
        final ImageButton loginByGooglePlusButton = findViewById(R.id.image_button_login_by_google);
        final ImageButton loginByVKontakteButton = findViewById(R.id.image_button_login_by_vkontakte);
        final ImageButton loginByFacebookButton = findViewById(R.id.image_button_login_by_facebook);

        initBottomSheet();

        loginByApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editLogin.getText().toString();
                String password = editPassword.getText().toString();
                loginWithUserData(email, password);
            }
        });

        facebookBaseLoginButton = findViewById(R.id.login_facebook_button);
        facebookBaseLoginButton.setReadPermissions("public_profile");

        loginByGooglePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by google plus", Toast.LENGTH_SHORT).show();
            }
        });

        loginByVKontakteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by vkontakte", Toast.LENGTH_SHORT).show();
                VKSdk.login(LoginActivity.this);
            }
        });

        loginByFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by facebook", Toast.LENGTH_SHORT).show();
                loginFacebookRequest();
                facebookBaseLoginButton.performClick();
            }
        });

    }

    private void initBottomSheet() {
        final View sheet = findViewById(R.id.bottom_registration_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(sheet);

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

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CDA", bottomSheetBehavior.getState() + "");
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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void openInteractionActivity() {
        Intent openMain = new Intent(LoginActivity.this, MainActivity.class);
        openMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMain);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_50"));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

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
                                account.setLoggedBy(Account.LOGGED_BY_VKONTAKTE);
                                login(account);
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

    private void loginWithUserData(String email, String password) {

        String[] args = new String[2];
        args[0] = email;
        args[1] = password;

        loginApplicationRequest(args);

    }

    private void loginApplicationRequest(final String[] requestArgs) {
        RequestPoll requestPoll = application.getRequestPoll();
        Request request = new Request("client", "server", "login", requestArgs);
        request.executeWithListener(new ServerCallback() {
            @Override
            public void onComplete(Response response) {

                if (response.getFuncName().equals("login")) {
                    String[] args = response.getFuncArgs();

                    if (args[0].equals("ok")) {
                        String name = args[1];
                        Account account = new Account();
                        account.setEmail(requestArgs[0]);
                        account.setPassword(requestArgs[1]);
                        account.setName(name);
                        account.setLoggedBy(Account.LOGGED_BY_APPLICATION);
                        login(account);
                    }

                    if (args[0].equals("error")) {
                        Tasks.ShowToast showToastAsync = new Tasks.ShowToast("Не могу войти", LoginActivity.this);
                        showToastAsync.execute();
                    }
                }
            }
        });
        requestPoll.execute(request);
    }

    private void loginFacebookRequest() {
        facebookBaseLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String first_name = object.getString("first_name");
                            String url_photo = "https://graph.facebook.com/" + object.getString("id") + "/picture?width=50&height=50";

                            Account account = new Account();
                            account.setName(first_name);
                            account.setUrlPhoto(url_photo);
                            account.setLoggedBy(Account.LOGGED_BY_FACEBOOK);
                            login(account);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name");
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

    private void login(Account account) {
        application.login(account);
        openInteractionActivity();
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else
            super.onBackPressed();
    }

}