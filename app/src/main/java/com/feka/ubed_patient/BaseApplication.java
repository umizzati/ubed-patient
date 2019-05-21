package com.feka.ubed_patient;

import android.app.Application;
import com.feka.ubed_patient.utils.SPUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class BaseApplication extends Application {

    public static FirebaseFirestore fireStoreDB;

    @Override
    public void onCreate() {
        super.onCreate();
        fireStoreDB = FirebaseFirestore.getInstance();
        SPUtils.getInstance().init(getApplicationContext()); // Shared Preferences singleton
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Montserrat-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
}
