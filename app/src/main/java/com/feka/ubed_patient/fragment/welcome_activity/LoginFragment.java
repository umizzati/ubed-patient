package com.feka.ubed_patient.fragment.welcome_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.feka.ubed_patient.R;

public class LoginFragment extends Fragment {

    private loginListener listener;
    public interface loginListener{
        void onLogin(String name, String password);
    }

    Button loginBtn;
    EditText email, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        loginBtn = v.findViewById(R.id.loginBtn);
        email = v.findViewById(R.id.email);
        password = v.findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLogin(email.getText().toString(), password.getText().toString());
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof loginListener) {
            listener = (loginListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement LoginFragment.loginListener");
        }
    }


}