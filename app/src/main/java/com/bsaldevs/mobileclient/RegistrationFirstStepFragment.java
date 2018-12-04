package com.bsaldevs.mobileclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bsaldevs.mobileclient.Activities.FirstConnectionActivity;
import com.bsaldevs.mobileclient.Activities.SplashActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFirstStepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFirstStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFirstStepFragment extends android.support.v4.app.Fragment implements RegistrationFragment.OnFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;

    public RegistrationFirstStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFirstStepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFirstStepFragment newInstance(String param1, String param2) {
        RegistrationFirstStepFragment fragment = new RegistrationFirstStepFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_registration_first_step, container, false);
        TextView title = view.findViewById(R.id.textView10);
        EditText editLogin = view.findViewById(R.id.editText2);
        EditText editPassword = view.findViewById(R.id.editText4);
        Button login = view.findViewById(R.id.button9);
        TextView titleLoginBy = view.findViewById(R.id.textView9);
        ImageButton loginByFacebookButton = view.findViewById(R.id.imageButtonFacebook);
        ImageButton loginByGooglePlusButton = view.findViewById(R.id.imageButtonGoogle);
        ImageButton loginByVKButton = view.findViewById(R.id.imageButtonVK);
        View registrationSheet = view.findViewById(R.id.bottomRegistrationSheet);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getContext(), FirstConnectionActivity.class);
                startActivity(login);
            }
        });

        loginByFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Login by facebook", Toast.LENGTH_SHORT).show();
            }
        });

        loginByGooglePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Login by google plus", Toast.LENGTH_SHORT).show();
            }
        });

        loginByVKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Login by vk", Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void register(View view) {
        Toast.makeText(view.getContext(), "Аккаунт создан", Toast.LENGTH_SHORT).show();
    }
}
