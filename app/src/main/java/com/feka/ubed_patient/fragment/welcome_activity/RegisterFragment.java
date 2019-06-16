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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class RegisterFragment extends Fragment {

    EditText name, email, tel_num, no_ic, password, confirm_password;
    String _name, _email, _tel_num, _no_ic, _password, _confirm_password;
    Button registerBtn;
    FrameLayout registerProgressbar;
    
    private registerListener listener;
    public interface registerListener{
        void onSuccessRegister();
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
        registerProgressbar = v.findViewById(R.id.registerProgressbar);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()){
                    run_progress();
                    onRegister();
                }else{
                    invalidMsg();
                }
            }
        });
        return v;
    }

    private void onRegister() {
        long epoch = System.currentTimeMillis()/1000;
        User users = new User(_name, _email, _tel_num, _no_ic, _password, true, epoch, epoch);

        BaseApplication.fireStoreDB.collection("users")
                .add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onSuccessRegister();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        invalidMsg();
                    }
                });
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

    private void invalidMsg(){
        Toast.makeText(getActivity(), "Failed to register! ", Toast.LENGTH_SHORT).show();
        stop_progress();
    }

    public void run_progress() {
        registerBtn.setVisibility(View.GONE);
        registerProgressbar.setVisibility(View.VISIBLE);
    }

    public void stop_progress() {
        registerProgressbar.setVisibility(View.GONE);
        registerBtn.setVisibility(View.VISIBLE);
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
