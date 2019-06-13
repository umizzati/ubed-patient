package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.activity.BaseActivity;
import com.feka.ubed_patient.adapter.BedAdapter;
import com.feka.ubed_patient.model.Bed;
import com.feka.ubed_patient.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class BedFragment extends Fragment {

    AlertDialog mDialogForm;
    ListView bedListView;
    FloatingActionButton bedAddBtn;
    BedAdapter mBedAdapter;
    ArrayList<Bed> mBedList = new ArrayList<>();
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    EditText dateET;
    android.support.v7.widget.AppCompatSpinner specialistSpinner;
    User mCurrentUser;

    public interface OnFragmentInteractionListener {
        void onSuccessAddBed();
        void onFailedAddBed();
    }
    private OnFragmentInteractionListener mListener;

    public BedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = BaseActivity.user;
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

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        updatBedsList();
        getBeds();

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
        final FrameLayout progressBar = dialogView.findViewById(R.id.bed_progress_btn);
        final EditText patient_id = dialogView.findViewById(R.id.form_bed_patient);
        specialistSpinner = dialogView.findViewById(R.id.form_bed_specialist);
        dateET = dialogView.findViewById(R.id.form_bed_date);
        final EditText note = dialogView.findViewById(R.id.form_bed_note);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Bed bed = new Bed(mCurrentUser.getName(), "1234", patient_id.getText().toString(), specialistSpinner.getSelectedItem().toString(), dateET.getText().toString(), note.getText().toString());
                CreateNewBed(bed);
            }
        });

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Objects.requireNonNull(getActivity()), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mDialogForm = dialogBuilder.create();
        mDialogForm.show();
    }

    // add new appointment
    private void CreateNewBed(Bed bed) {
        BaseApplication.fireStoreDB.collection("beds")
                .add(bed)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mDialogForm.dismiss();
                        mListener.onSuccessAddBed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mListener.onFailedAddBed();
                    }
                });
    }

    private void updateLabel() {
        String myFormat = "EEE, dd MMMM yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateET.setText(sdf.format(myCalendar.getTime()));
    }

    private void getBeds() {
        Query query = BaseApplication.fireStoreDB.collection("beds");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(Objects.requireNonNull(task.getResult()).size() > 0){
                    mBedList = (ArrayList<Bed>) task.getResult().toObjects(Bed.class);
                    mBedAdapter.updateAdapter(mBedList);
                }
            }
        });
    }

    private void updatBedsList(){
        final Query query = BaseApplication.fireStoreDB.collection("beds");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null) {
                    mBedList = (ArrayList<Bed>) snapshot.toObjects(Bed.class);
                    mBedAdapter.updateAdapter(mBedList);
                }
            }
        });
    }

}
