package com.feka.ubed_patient.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.welcome_activity.LoginFragment;
import com.feka.ubed_patient.fragment.welcome_activity.RegisterFragment;
import com.feka.ubed_patient.fragment.welcome_activity.WelcomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class WelcomeActivity extends AppCompatActivity implements
        WelcomeFragment.welcomeListener,
        LoginFragment.loginListener,
        RegisterFragment.registerListener{

    private Fragment loginFragment;
    private Fragment registerFragment;
    private Fragment welcomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeFragment = new WelcomeFragment();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // this for fonts
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.welcomePlaceholder, welcomeFragment)
                .commit();
    }

    @Override
    public void onWelcomeLoginClick() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.welcomePlaceholder, loginFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWelcomeRegisterClick() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.welcomePlaceholder, registerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLogin(String name, String password) {
        Query query = BaseApplication.fireStoreDB.collection("users").whereEqualTo("email", name).whereEqualTo("password", password);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(Objects.requireNonNull(task.getResult()).size() > 0){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid email or password! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRegister(String name, String email, String tel_num, String ic, String password) {

    }
}
