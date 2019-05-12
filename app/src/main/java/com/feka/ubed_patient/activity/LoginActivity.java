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
        //login to firestore
        DocumentReference docRef = BaseApplication.fireStoreDB.collection("users").document("uwki0aPpuxl6ArBYesa5");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("doc", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("doc", "No such document");
                    }
                } else {
                    Log.d("doc", "get failed with ", task.getException());
                }
            }
        });

//        db.collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("login", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w("login", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
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

