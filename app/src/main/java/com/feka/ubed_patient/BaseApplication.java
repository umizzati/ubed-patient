package com.feka.ubed_patient;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;

public class BaseApplication extends Application {


    public static FirebaseFirestore db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseFirestore.getInstance();
    }
}
