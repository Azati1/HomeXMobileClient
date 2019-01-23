package com.bsaldevs.mobileclient.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.Net.Response;
import com.bsaldevs.mobileclient.Net.ServerCallback;
import com.bsaldevs.mobileclient.R;

public class RegistrationFragment extends android.support.v4.app.Fragment {

    private MyApplication application;
    private OnFragmentInteractionListener mListener;

    public RegistrationFragment() {

    }

    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication) getContext().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_registration, container,false);
        final TextView titleName = view.findViewById(R.id.textView11);
        final EditText editName = view.findViewById(R.id.edit_text_name);
        final TextView titleEmail = view.findViewById(R.id.textView12);
        final EditText editEmail = view.findViewById(R.id.edit_text_email);
        final TextView titlePassword = view.findViewById(R.id.textView15);
        final EditText editPassword = view.findViewById(R.id.edit_text_password);
        final Button buttonRegistration = view.findViewById(R.id.button_registration);
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Создаю аккаунт", Toast.LENGTH_SHORT).show();

                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                register(name, email, password);
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void register(String name, String email, String password) {

        String args[] = new String[] {name, email, password};

        RequestPoll requestPoll = application.getRequestPoll();
        Request request = new Request("client", "server", "register", args);
        request.executeWithListener(new ServerCallback() {
            @Override
            public void onComplete(Response response) {

                if (response.getFuncName().equals("register")) {
                    String[] args = response.getFuncArgs();
                    if (args[0].equals("ok")) {
                        ShowToastAsync showToastAsync = new ShowToastAsync("Аккаунт успешно создан");
                        showToastAsync.execute();
                    }
                    if (args[0].equals("error")) {
                        ShowToastAsync showToastAsync = new ShowToastAsync("Произошла ошибка при создании аккаунта");
                        showToastAsync.execute();
                    }
                }
            }
        });
        requestPoll.execute(request);
    }

    private class ShowToastAsync extends AsyncTask<Void, Void, Void> {

        private String value;

        public ShowToastAsync(String value) {
            this.value = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
        }
    }
    
}