package com.feka.ubed_patient.fragment.main_activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        List<String> strings = Arrays.asList("sup1", "sup2", "sup3");
        mBedAdapter = new BedAdapter(getActivity(), strings);
        bedListView.setAdapter(mBedAdapter);
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
