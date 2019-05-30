package com.feka.ubed_patient.fragment.main_activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.adapter.AppointmentAdapter;

import java.util.Arrays;
import java.util.List;

public class AppointmentFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    ListView appointmentListView;
    FloatingActionButton appointmentAddBtn;
    AppointmentAdapter mAppointmentAdapter;
    public AppointmentFragment() {
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appoinment, container, false);
        appointmentListView = v.findViewById(R.id.appointment_listview);
        appointmentAddBtn = v.findViewById(R.id.appointment_add_btn);
        List<String> strings = Arrays.asList("sup1", "sup2", "sup3");
        mAppointmentAdapter = new AppointmentAdapter(getContext(), strings);
        appointmentListView.setAdapter(mAppointmentAdapter);
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
}
