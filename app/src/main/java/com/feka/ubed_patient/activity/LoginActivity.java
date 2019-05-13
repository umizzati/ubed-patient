package com.feka.ubed_patient.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.login_activity.LoginFragment;
import com.feka.ubed_patient.fragment.login_activity.RegisterFragment;
import com.feka.ubed_patient.model.Patient;
import com.feka.ubed_patient.utils.SPUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginFragment.loginListener {

    FrameLayout placeholder;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //assign view object
        placeholder = findViewById(R.id.login_placeholder);
        progressBar = findViewById(R.id.login_progressbar);

        performLoginCheck();
    }

    private void performLoginCheck() {
        if (isLogin()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.login_placeholder, new LoginFragment())
                    .commit();
        }
    }


    private boolean isLogin() {
        return false;
    }

    @Override
    public void onRegister() {
        loadRegister();
    }

    @Override
    public void onLogin(String name, String password) {
        loadProgressBar();

        Query query = BaseApplication.fireStoreDB.collection("users").whereEqualTo("email", name).whereEqualTo("password", password);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(Objects.requireNonNull(task.getResult()).size() > 0){
                    Toast.makeText(getApplicationContext(), "User email already exist in the database ", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("db", "Not found");
                }
            }
        });
    }

    private void performRegistering(Patient patient){
        //registering patient to firestore
    }

    private void loadProgressBar(){
        placeholder.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void loadPlaceholder(){
        placeholder.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void loadRegister(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_placeholder, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

}

