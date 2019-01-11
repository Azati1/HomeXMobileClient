package com.bsaldevs.mobileclient.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bsaldevs.mobileclient.Activities.LoginActivity;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.Net.Response;
import com.bsaldevs.mobileclient.Net.ServerCallback;
import com.bsaldevs.mobileclient.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private MyApplication application;
    private OnFragmentInteractionListener mListener;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
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
        View view = inflater.inflate(R.layout.fragment_registration, container,false);
        TextView titleName = view.findViewById(R.id.textView11);
        final EditText editName = view.findViewById(R.id.edit_text_name);
        TextView titleEmail = view.findViewById(R.id.textView12);
        final EditText editEmail = view.findViewById(R.id.edit_text_email);
        TextView titlePassword = view.findViewById(R.id.textView15);
        final EditText editPassword = view.findViewById(R.id.edit_text_password);
        Button buttonRegistration = view.findViewById(R.id.button_registration);
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
        /*final ImageView slideArrow = view.findViewById(R.id.image_slide_sheet_arrow);
        final Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_slide_arrow);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideArrow.startAnimation(rotation);
            }
        });*/
        Log.d("CDA", "nested fragment onCreate");
        /*makeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });*/

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*private void register(String name, String email, String password) {

        final TCPConnection connection = application.getClient().getConnection();

        String args[] = new String[] {name, email, password};

        Request request = new Request("client", "server", "register", args);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        final String jsonRequest = gson.toJson(request);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                connection.sendString(jsonRequest);
            }
        });

        thread.start();

    }*/

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
                        ShowToast showToast = new ShowToast("Аккаунт успешно создан");
                        showToast.execute();
                    }
                    if (args[0].equals("error")) {
                        ShowToast showToast = new ShowToast("Произошла ошибка при создании аккаунта");
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
            Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
        }
    }
    
}
