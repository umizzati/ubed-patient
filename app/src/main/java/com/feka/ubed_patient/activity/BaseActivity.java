package com.feka.ubed_patient.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.main_activity.AppointmentFragment;
import com.feka.ubed_patient.fragment.main_activity.BedFragment;
import com.feka.ubed_patient.fragment.main_activity.HomeFragment;
import com.feka.ubed_patient.fragment.main_activity.ReviewFragment;
import com.feka.ubed_patient.fragment.main_activity.SettingFragment;
import com.feka.ubed_patient.model.User;
import com.feka.ubed_patient.utils.SPUtils;
import com.google.gson.Gson;

import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class BaseActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        BedFragment.OnFragmentInteractionListener,
        AppointmentFragment.OnFragmentInteractionListener,
        ReviewFragment.OnFragmentInteractionListener {
    HomeFragment homeFragment;
    BedFragment bedFragment;
    AppointmentFragment appointmentFragment;
    ReviewFragment reviewFragment;
    SettingFragment settingFragment;
    BottomNavigationView navView;
    public static User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
                    updatePage(homeFragment);
                    return true;
                case R.id.navigation_bed:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("UBed");
                    updatePage(bedFragment);
                    return true;
                case R.id.navigation_appointment:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Appointment");
                    updatePage(appointmentFragment);
                    return true;
                case R.id.navigation_review:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Review");
                    updatePage(reviewFragment);
                    return true;
            }
            return false;
        }
    };

    private void updatePage(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePlaceholder, fragment)
                .commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // this for fonts
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        homeFragment = new HomeFragment();
        bedFragment = new BedFragment();
        appointmentFragment = new AppointmentFragment();
        settingFragment = new SettingFragment();
        reviewFragment = new ReviewFragment();

        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");

        Gson gson = new Gson();
        String user_json = SPUtils.getInstance().getSP().getString(Constant.USER_JSON, "");
        user = gson.fromJson(user_json, User.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePlaceholder, homeFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onHomeAppointment() {
        navView.setSelectedItemId(R.id.navigation_appointment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePlaceholder, appointmentFragment)
                .commit();
    }

    @Override
    public void onHomeBed() {
        navView.setSelectedItemId(R.id.navigation_bed);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePlaceholder, bedFragment)
                .commit();
    }

    @Override
    public void onHomeAboutUs() {

    }

    @Override
    public void onSuccessFeedback() {
        Toast.makeText(this, "Thank you for your review!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedFeedback() {
        Toast.makeText(this, "Something when wrong. Please check your connection!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignOut() {
        SPUtils.getInstance().getSPE().clear();
        SPUtils.getInstance().getSPE().apply();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                finish();
            }
        }, 2000);

    }

}
