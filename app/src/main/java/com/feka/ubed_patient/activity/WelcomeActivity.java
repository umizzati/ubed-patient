package com.feka.ubed_patient.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.AboutUsFragment;
import com.feka.ubed_patient.fragment.LocateUsFragment;
import com.feka.ubed_patient.fragment.welcome_activity.LoginFragment;
import com.feka.ubed_patient.fragment.welcome_activity.RegisterFragment;
import com.feka.ubed_patient.fragment.welcome_activity.WelcomeFragment;
import com.feka.ubed_patient.model.User;
import com.feka.ubed_patient.utils.SPUtils;
import com.google.gson.Gson;

import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class WelcomeActivity extends AppCompatActivity implements
        WelcomeFragment.welcomeListener,
        LoginFragment.loginListener,
        RegisterFragment.registerListener,
        AboutUsFragment.OnFragmentInteractionListener,
        LocateUsFragment.OnFragmentInteractionListener {

    private Fragment loginFragment;
    private Fragment registerFragment;
    private Fragment welcomeFragment;
    private AboutUsFragment aboutUsFragment;
    private LocateUsFragment locateUsFragment;
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeFragment = new WelcomeFragment();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        aboutUsFragment = new AboutUsFragment();
        locateUsFragment = new LocateUsFragment();

        navView = findViewById(R.id.nav_view_1);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_1_home:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.welcomePlaceholder, welcomeFragment)
                            .commit();
                    return true;
                case R.id.navigation_1_about_us:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("About Us");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.welcomePlaceholder, aboutUsFragment)
                            .commit();
                    return true;
                case R.id.navigation_1_locate_us:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Locate Us");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.welcomePlaceholder, locateUsFragment)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        // this for fonts
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check if already login
        if (isLogin()){
            toBaseActivity();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.welcomePlaceholder, welcomeFragment)
                    .commit();
        }

    }

    private boolean isLogin() {
        return SPUtils.getInstance().getSP().getBoolean(Constant.USER_EXISTS, false);
    }

    @Override
    public void onWelcomeLoginClick() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.welcomePlaceholder, loginFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWelcomeRegisterClick() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.welcomePlaceholder, registerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSuccessLogin(User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        Log.e("User", userJson);
        SPUtils.getInstance().getSPE().putString(Constant.USER_JSON, userJson);
        SPUtils.getInstance().getSPE().putBoolean(Constant.USER_EXISTS, true);
        SPUtils.getInstance().getSPE().apply();
        toBaseActivity();
    }

    private void toBaseActivity() {
        startActivity(new Intent(getApplicationContext(), BaseActivity.class));
        finish();
    }


    @Override
    public void onSuccessRegister() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Toast.makeText(this, "Success create account. Please login", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().popBackStackImmediate();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
