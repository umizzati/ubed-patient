package com.feka.ubed_patient;

import android.app.Application;

import com.feka.ubed_patient.utils.FireStoreUtils;
import com.feka.ubed_patient.utils.SPUtils;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseApplication extends Application {


    public static FirebaseFirestore fireStoreDB;

    @Override
    public void onCreate() {
        super.onCreate();
        fireStoreDB = FirebaseFirestore.getInstance();
        SPUtils.getInstance().init(getApplicationContext()); // Shared Preferences singleton
    }
}
