package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.adapter.BedAdapter;

import java.util.Arrays;
import java.util.List;

public class BedFragment extends Fragment {

    ListView bedListView;
    FloatingActionButton bedAddBtn;
    BedAdapter mBedAdapter;


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
        List<String> strings = Arrays.asList("sup1", "sup2", "sup3");
        mBedAdapter = new BedAdapter(getActivity(), strings);
        bedListView.setAdapter(mBedAdapter);

        // add bed view
        bedAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateBedForm();
            }
        });
        return v;
    }

    private void initiateBedForm() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_bed, null);
        dialogBuilder.setView(dialogView);

        // action view
        Button bookBtn = dialogView.findViewById(R.id.bedBookBtn);
        ProgressBar progressBar = dialogView.findViewById(R.id.bedProgressBar);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
//        editText.setText("test label");
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
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
