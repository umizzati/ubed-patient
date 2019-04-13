package com.feka.ubed_patient.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.LoginFragment;
import com.feka.ubed_patient.fragment.RegisterFragment;
import com.feka.ubed_patient.model.Patient;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        performLoginCheck();

    }

    private void performLoginCheck() {
        if (isLogin()){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.login_placeholder, new LoginFragment()).commit();
        }
    }

    private void performRegistering(Patient patient){
        //registering patient to firestore
    }

    private void loadRegister(){
        getSupportFragmentManager().beginTransaction().replace(R.id.login_placeholder, new RegisterFragment()).commit();
    }

    private boolean isLogin() {
        return true;
    }

}

