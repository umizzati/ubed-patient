package com.feka.ubed_patient.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.welcome_activity.LoginFragment;
import com.feka.ubed_patient.fragment.welcome_activity.RegisterFragment;
import com.feka.ubed_patient.fragment.welcome_activity.WelcomeFragment;
import com.feka.ubed_patient.model.User;
import com.feka.ubed_patient.utils.SPUtils;
import com.google.gson.Gson;

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
}
