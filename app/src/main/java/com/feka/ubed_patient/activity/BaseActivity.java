package com.feka.ubed_patient.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.AboutUsFragment;
import com.feka.ubed_patient.fragment.LocateUsFragment;
import com.feka.ubed_patient.fragment.main_activity.AppointmentFragment;
import com.feka.ubed_patient.fragment.main_activity.BedFragment;
import com.feka.ubed_patient.fragment.main_activity.HomeFragment;
import com.feka.ubed_patient.fragment.main_activity.ReviewFragment;
import com.feka.ubed_patient.fragment.main_activity.SettingFragment;
import com.feka.ubed_patient.fragment.welcome_activity.WelcomeFragment;
import com.feka.ubed_patient.model.User;
import com.feka.ubed_patient.utils.SPUtils;
import com.google.gson.Gson;

import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class BaseActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        BedFragment.OnFragmentInteractionListener,
        AppointmentFragment.OnFragmentInteractionListener,
        ReviewFragment.OnFragmentInteractionListener,
        WelcomeFragment.welcomeListener, AboutUsFragment.OnFragmentInteractionListener, LocateUsFragment.OnFragmentInteractionListener {
    HomeFragment homeFragment;
    BedFragment bedFragment;
    AppointmentFragment appointmentFragment;
    ReviewFragment reviewFragment;
    SettingFragment settingFragment;
    BottomNavigationView navView;
    WelcomeFragment welcomeFragment;
    AboutUsFragment aboutUsFragment;
    LocateUsFragment locateUsFragment;
    AlertDialog mDialogHome;
    public static User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
                    updatePage(welcomeFragment);
                    return true;
                case R.id.navigation_about_us:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("About Us");
                    updatePage(aboutUsFragment);
                    return true;
                case R.id.navigation_locate_us:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Locate Us");
                    updatePage(locateUsFragment);
                    return true;
                case R.id.navigation_review:
                Objects.requireNonNull(getSupportActionBar()).setTitle("Review");
                    updatePage(reviewFragment);
                    return true;
                case R.id.navigation_signout:
                    signOut();
                    return false;
            }
            return false;
        }
    };

    private void signOut() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_signout, null);
        final Button signOutBtn = dialogView.findViewById(R.id.signout_btn);
        final FrameLayout progressBar = dialogView.findViewById(R.id.signout_progressbar);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                onSignOut();
            }
        });
        dialogBuilder.setView(dialogView);

        // action view
        mDialogHome = dialogBuilder.create();
        mDialogHome.show();
    }

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
        welcomeFragment = new WelcomeFragment();
        locateUsFragment = new LocateUsFragment();
        aboutUsFragment = new AboutUsFragment();

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
                .replace(R.id.basePlaceholder, welcomeFragment)
                .commit();
    }

    @Override
    public void onHomeAppointment() {
//        navView.setSelectedItemId(R.id.navigation_appointment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePlaceholder, appointmentFragment)
                .commit();
    }

    @Override
    public void onHomeBed() {
//        navView.setSelectedItemId(R.id.navigation_bed);
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
    public void onSurvey() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        navView.setSelectedItemId(R.id.navigation_home);
        Uri uriUrl = Uri.parse("http://bit.ly/feedback-ubed");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
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

    @Override
    public void onSuccessAddBed() {
        Toast.makeText(this, "Your bed booking was created!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedAddBed() {
        Toast.makeText(this, "Something when wrong. Please contact administration!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessAddApp() {
        Toast.makeText(this, "Your appointment booking was created!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedAddApp() {
        Toast.makeText(this, "Something when wrong. Please contact administration!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWelcomeLoginClick() {

    }

    @Override
    public void onWelcomeRegisterClick() {

    }

    @Override
    public void onWelcomeBedClick() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("UBed");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePlaceholder, bedFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWelcomeAppClick() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Appointment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePlaceholder, appointmentFragment)
                .addToBackStack(null)
                .commit();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
