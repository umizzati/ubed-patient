package com.feka.ubed_patient.fragment.welcome_activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.feka.ubed_patient.R;

public class WelcomeFragment extends Fragment{

    public welcomeListener listener;
    public interface welcomeListener{
        void onWelcomeLoginClick();
        void onWelcomeRegisterClick();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        Button loginBtn = v.findViewById(R.id.welcomeLoginBtn);
        Button registerBtn = v.findViewById(R.id.welcomeRegisterBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWelcomeLoginClick();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWelcomeRegisterClick();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof welcomeListener) {
            listener = (welcomeListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement WelcomeFragment.welcomeListener");
        }
    }
}