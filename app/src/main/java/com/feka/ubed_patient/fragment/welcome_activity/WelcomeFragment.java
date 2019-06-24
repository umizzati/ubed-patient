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
import android.widget.TextView;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.model.Count;
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
    }


    ColorfulRingProgressView bedSPV, appSPV;
    TextView bedNum, appNum;
    int bedGetNum, appGetNum;
    ArrayList<Count> mCount;


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

        updateProgressNum();
        getProgress();

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