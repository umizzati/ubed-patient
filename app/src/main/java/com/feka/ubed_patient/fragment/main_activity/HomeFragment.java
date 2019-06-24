package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.activity.BaseActivity;
import com.feka.ubed_patient.model.Count;
import com.feka.ubed_patient.model.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.timqi.sectorprogressview.ColorfulRingProgressView;
import com.timqi.sectorprogressview.SectorProgressView;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomeFragment extends Fragment {

    TextView mAppointment;
    TextView mBed;
    TextView mAboutUs;
    TextView mFeedback;
    TextView mSignOut;
    AlertDialog mDialogHome;
    ColorfulRingProgressView bedSPV, appSPV;
    TextView bedNum, appNum;
    int bedGetNum, appGetNum;
    ArrayList<Count> mCount;

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onHomeAppointment();
        void onHomeBed();
        void onHomeAboutUs();
        void onSuccessFeedback();
        void onFailedFeedback();
        void onSignOut();
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mAppointment = v.findViewById(R.id.home_appointment_btn);
        mBed = v.findViewById(R.id.home_ubed_btn);
        mAboutUs = v.findViewById(R.id.home_aboutus_btn);
        mFeedback = v.findViewById(R.id.home_feedback_btn);
        mSignOut = v.findViewById(R.id.home_signout_btn);
        bedNum = v.findViewById(R.id.bed_num);
        appNum = v.findViewById(R.id.app_num);
        bedSPV = v.findViewById(R.id.bed_spv);
        appSPV = v.findViewById(R.id.app_spv);

        mAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onHomeAppointment();
            }
        });

        mBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onHomeBed();
            }
        });

        mAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onHomeAboutUs();
            }
        });

        mFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackDialog();
            }
        });

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignOut();
            }
        });

        updateProgressNum();
        getProgress();
//        refreshProgress();
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

    private void refreshProgress() {
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
            appNum.setText(String.format("%d / %d", appGetNum, mCount.get(0).getNumber()));
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void feedbackDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_feedback, null);
        final RatingBar ratingBar = dialogView.findViewById(R.id.feedback_rating_bar);
        final EditText editText = dialogView.findViewById(R.id.feedback_review);
        final Button btn = dialogView.findViewById(R.id.feedback_btn);
        final FrameLayout progressBar = dialogView.findViewById(R.id.feedback_progressbar);
        dialogBuilder.setView(dialogView);

        //action
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Review review = new Review(BaseActivity.user.getName(), editText.getText().toString(), ratingBar.getRating(), "");
                review.setUser_id(BaseActivity.user.getUser_id());
                addNewReview(review);
            }
        });

        // action view
        mDialogHome = dialogBuilder.create();
        mDialogHome.show();
    }

    private void addNewReview(Review review) {
        BaseApplication.fireStoreDB.collection("reviews")
                .add(review)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mDialogHome.dismiss();
                        mListener.onSuccessFeedback();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mListener.onFailedFeedback();
                    }
                });
    }

    private void onSignOut() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_signout, null);
        final Button signOutBtn = dialogView.findViewById(R.id.signout_btn);
        final FrameLayout progressBar = dialogView.findViewById(R.id.signout_progressbar);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                mListener.onSignOut();
            }
        });
        dialogBuilder.setView(dialogView);

        // action view
        mDialogHome = dialogBuilder.create();
        mDialogHome.show();
    }

//    public void hideSoftKeyboard() {
//        if (Objects.requireNonNull(getActivity()).getCurrentFocus()!=null) {
//            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(), 0);
//        }
//    }

}
