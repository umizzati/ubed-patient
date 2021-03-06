package com.feka.ubed_patient.fragment.welcome_activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.activity.BaseActivity;
import com.feka.ubed_patient.model.Count;
import com.feka.ubed_patient.model.User;
import com.feka.ubed_patient.utils.SPUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

import java.util.ArrayList;
import java.util.Objects;

public class WelcomeFragment extends Fragment{

    public welcomeListener listener;
    public interface welcomeListener{
        void onWelcomeLoginClick();
        void onWelcomeRegisterClick();
        void onWelcomeBedClick();
        void onWelcomeAppClick();
    }


    ColorfulRingProgressView bedSPV, appSPV;
    TextView bedNum, appNum;
    int bedGetNum, appGetNum;
    ArrayList<Count> mCount;
    LinearLayout loginLayout, homeLayout;
    User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        Button loginBtn = v.findViewById(R.id.welcomeLoginBtn);
        Button registerBtn = v.findViewById(R.id.welcomeRegisterBtn);

        Button ubed = v.findViewById(R.id.ubed_btn);
        Button appointment = v.findViewById(R.id.app_btn);

        TextView welcomeHi = v.findViewById(R.id.welcome_hi);
        TextView welcomeBedManager = v.findViewById(R.id.welcomeBedManager);

        loginLayout = v.findViewById(R.id.btn_login_layout);
        homeLayout = v.findViewById(R.id.btn_home_layout);

        bedNum = v.findViewById(R.id.bed_num);
        appNum = v.findViewById(R.id.app_num);
        bedSPV = v.findViewById(R.id.bed_spv);
        appSPV = v.findViewById(R.id.app_spv);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWelcomeLoginClick();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWelcomeRegisterClick();
            }
        });

        ubed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWelcomeBedClick();
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWelcomeAppClick();
            }
        });

        updateProgressNum();
        getProgress();

        boolean isLogin = SPUtils.getInstance().getSP().getBoolean(Constant.USER_EXISTS, false);
        if (isLogin){
            mUser = BaseActivity.user;
            loginLayout.setVisibility(View.GONE);
            homeLayout.setVisibility(View.VISIBLE);
            welcomeHi.setText(String.format("Hi %s", mUser.getName()));
            if(mUser.isAdmin()){
                welcomeBedManager.setVisibility(View.VISIBLE);
            }else{
                welcomeBedManager.setVisibility(View.GONE);
            }

            welcomeHi.setVisibility(View.VISIBLE);
        }else{
            loginLayout.setVisibility(View.VISIBLE);
            homeLayout.setVisibility(View.GONE);
            welcomeHi.setVisibility(View.GONE);
        }


        return v;
    }

    private void updateProgressNum() {
        Query countQuery =  BaseApplication.fireStoreDB.collection("counts");
        countQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mCount = (ArrayList<Count>) task.getResult().toObjects(Count.class);
            }
        });

        countQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null) {
                    mCount = (ArrayList<Count>) snapshot.toObjects(Count.class);
                    updateAppProgress();
                    updateBedProgress();
                }
            }
        });
    }


    private void getProgress() {
        //bed
        Query bedQuery =  BaseApplication.fireStoreDB.collection("beds")
                .whereEqualTo("deleted", false)
                .whereEqualTo("status", Constant.BOOKING_APPROVED);
        bedQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                bedGetNum = Objects.requireNonNull(task.getResult()).size();
                updateBedProgress();
            }
        });

        Query appQuery =  BaseApplication.fireStoreDB.collection("appointments")
                .whereEqualTo("deleted", false)
                .whereEqualTo("status", Constant.BOOKING_APPROVED);
        appQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                appGetNum = Objects.requireNonNull(task.getResult()).size();
                updateAppProgress();
            }
        });

        bedQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null) {
                    bedGetNum = snapshot.size();
                    updateBedProgress();
                }
            }
        });

        appQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null) {
                    appGetNum = snapshot.size();
                    updateAppProgress();
                }
            }
        });

    }

    private void updateBedProgress() {
        if (mCount != null){
            float percent = (float) bedGetNum / mCount.get(1).getNumber() * 100;
            bedSPV.setPercent(percent);
            bedNum.setText(String.format("%d / %d", bedGetNum, mCount.get(1).getNumber()));
        }
    }

    private void updateAppProgress() {
        if (mCount != null){
            float percent = (float) appGetNum / mCount.get(0).getNumber() * 100;
            appSPV.setPercent(percent);
            appNum.setText(String.format("%d", appGetNum));
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof welcomeListener) {
            listener = (welcomeListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement WelcomeFragment.welcomeListener");
        }
    }
}