package com.feka.ubed_patient.fragment.welcome_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.feka.ubed_patient.R;

public class RegisterFragment extends Fragment {

    EditText name, email, tel_num, no_ic, password, confirm_password;
    String _name, _email, _tel_num, _no_ic, _password, _confirm_password;
    Button registerBtn;
    
    private registerListener listener;
    public interface registerListener{
        void onRegister(String name,
                        String email,
                        String tel_num,
                        String ic,
                        String password);
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        name = v.findViewById(R.id.sigup_name);
        email = v.findViewById(R.id.signup_email);
        tel_num = v.findViewById(R.id.signup_tel);
        no_ic = v.findViewById(R.id.signup_ic);
        password = v.findViewById(R.id.signup_password);
        confirm_password = v.findViewById(R.id.signup_confirm_password);
        registerBtn = v.findViewById(R.id.signup_button);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()){
                    listener.onRegister(_name, _email, _tel_num, _no_ic, _password);
                }
            }
        });
        return v;
    }

    // validate through all 
    private boolean isValidate() {
        _name = name.getText().toString().trim();
        _email = email.getText().toString().trim();
        _tel_num = tel_num.getText().toString().trim();
        _no_ic = no_ic.getText().toString().trim();
        _password = password.getText().toString().trim();
        _confirm_password = confirm_password.getText().toString().trim();

        if(_name == null || _name.equals(""))
            return false;

        if(_email == null || _email.equals(""))
            return false;

        if(_tel_num == null || _tel_num.equals(""))
            return false;

        if(_no_ic == null || _no_ic.equals(""))
            return false;

        if(_password == null || _password.equals(""))
            return false;

        return _password.equals(_confirm_password);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof registerListener) {
            listener = (registerListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement registerFragment.registerListener");
        }
    }
}
