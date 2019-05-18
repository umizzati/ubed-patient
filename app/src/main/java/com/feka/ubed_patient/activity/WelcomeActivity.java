package com.feka.ubed_patient.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.welcome_activity.LoginFragment;
import com.feka.ubed_patient.fragment.welcome_activity.RegisterFragment;
import com.feka.ubed_patient.fragment.welcome_activity.WelcomeFragment;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class WelcomeActivity extends AppCompatActivity implements WelcomeFragment.welcomeListener {

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
                .replace(R.id.login_placeholder, welcomeFragment)
                .commit();
    }

    @Override
    public void onWelcomeLoginClick() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_placeholder, loginFragment)
                .commit();
    }

    @Override
    public void onWelcomeRegisterClick() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_placeholder, registerFragment)
                .commit();
    }
}
