package com.feka.ubed_patient.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.fragment.main_activity.AppointmentFragment;
import com.feka.ubed_patient.fragment.main_activity.BedFragment;
import com.feka.ubed_patient.fragment.main_activity.HomeFragment;
import com.feka.ubed_patient.fragment.main_activity.SettingFragment;

public class BaseActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        BedFragment.OnFragmentInteractionListener,
        AppointmentFragment.OnFragmentInteractionListener{
    private TextView mTextMessage;
    HomeFragment homeFragment;
    BedFragment bedFragment;
    AppointmentFragment appointmentFragment;
    SettingFragment settingFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    updatePage(homeFragment);
                    return true;
                case R.id.navigation_bed:
                    updatePage(bedFragment);
                    return true;
                case R.id.navigation_appointment:
                    updatePage(appointmentFragment);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        homeFragment = new HomeFragment();
        bedFragment = new BedFragment();
        appointmentFragment = new AppointmentFragment();
        settingFragment = new SettingFragment();

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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction() {

    }
}
