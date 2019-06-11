package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feka.ubed_patient.R;

public class HomeFragment extends Fragment {

    TextView mAppointment;
    TextView mBed;
    TextView mAboutUs;
    TextView mFeedback;
    TextView mSignOut;
    AlertDialog mDialogFeedback;
    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onHomeAppointment();
        void onHomeBed();
        void onHomeAboutUs();
        void onHomeFeedback();
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
                mListener.onSignOut();
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
        dialogBuilder.setView(dialogView);

        // action view
        mDialogFeedback = dialogBuilder.create();
        mDialogFeedback.show();
    }

}
