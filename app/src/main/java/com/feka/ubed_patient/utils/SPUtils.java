package com.feka.ubed_patient.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

    private String SP_UBED_PATIENT = "SP_UBED_PATIENT";
    public static SharedPreferences sp;
    public static SharedPreferences.Editor spe;
    private static SPUtils me;
    public static SPUtils getInstance(){
        if (me == null){
            me = new SPUtils();
        }
        return me;
    }
    private SPUtils(){}
    public void init(Context context){
        sp = context.getSharedPreferences(SP_UBED_PATIENT, Context.MODE_PRIVATE);
        spe = sp.edit();
    }
    public SharedPreferences getSP(){ return sp; }
    public SharedPreferences.Editor getSPE(){ return spe; }
}
