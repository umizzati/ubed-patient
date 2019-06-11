package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.adapter.BedAdapter;
import com.feka.ubed_patient.model.Bed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BedFragment extends Fragment {

    AlertDialog mDialogForm;
    ListView bedListView;
    FloatingActionButton bedAddBtn;
    BedAdapter mBedAdapter;
    ArrayList<Bed> mBedList = new ArrayList<>();


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
    private OnFragmentInteractionListener mListener;

    public BedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bed, container, false);
        bedAddBtn = v.findViewById(R.id.bed_add_btn);
        bedListView = v.findViewById(R.id.bed_listview);

        // set list view and values
        mBedAdapter = new BedAdapter(getActivity(), mBedList);
        bedListView.setAdapter(mBedAdapter);

        // add bed view
        bedAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateForm();
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

    private void initiateForm() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_bed, null);
        dialogBuilder.setView(dialogView);

        // action view
        final Button bookBtn = dialogView.findViewById(R.id.bedBookBtn);
        final ProgressBar progressBar = dialogView.findViewById(R.id.bedProgressBar);
        final EditText patient_id = dialogView.findViewById(R.id.form_bed_patient);
        final EditText specialist = dialogView.findViewById(R.id.form_bed_specialist);
        final EditText date = dialogView.findViewById(R.id.form_bed_date);
        final EditText note = dialogView.findViewById(R.id.form_bed_note);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Bed bed = new Bed();
                CreateNewBed(bed);
            }
        });
        mDialogForm = dialogBuilder.create();
        mDialogForm.show();
    }

    // add new appointment
    private void CreateNewBed(Bed bed) {
        mDialogForm.dismiss();
        mBedList.add(bed);
        mBedAdapter.updateAdapter(mBedList);
    }
}
