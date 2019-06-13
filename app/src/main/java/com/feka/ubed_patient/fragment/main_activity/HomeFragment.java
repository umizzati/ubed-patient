package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.activity.BaseActivity;
import com.feka.ubed_patient.model.Review;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomeFragment extends Fragment {

    TextView mAppointment;
    TextView mBed;
    TextView mAboutUs;
    TextView mFeedback;
    TextView mSignOut;
    AlertDialog mDialogHome;
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
        return v;
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
                Review review = new Review(BaseActivity.user.getName(), editText.getText().toString(), ratingBar.getRating());
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
