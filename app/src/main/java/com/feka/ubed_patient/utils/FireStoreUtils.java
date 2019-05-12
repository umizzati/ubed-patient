package com.feka.ubed_patient.utils;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

public class FireStoreUtils {

    public static FirebaseFirestore db;
    public static FireStoreUtils me;
    public static FireStoreUtils getInstance(){
        if (me == null){
            me = new FireStoreUtils();
        }
        return me;
    }
    private FireStoreUtils(){}
    public void init(Context context){
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseFirestore getDb() { return db; }
}
