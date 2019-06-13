package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
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

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.adapter.AppointmentAdapter;
import com.feka.ubed_patient.model.Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AppointmentFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ListView mAppointmentListView;
    FloatingActionButton mAppointmentAddBtn;
    AppointmentAdapter mAppointmentAdapter;
    AlertDialog mDialogForm;
    ArrayList<Appointment> mAppointmentList = new ArrayList<>();
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    EditText dateET;
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
        mAppointmentListView = v.findViewById(R.id.appointment_listview);
        mAppointmentAddBtn = v.findViewById(R.id.appointment_add_btn);

        mAppointmentAdapter = new AppointmentAdapter(getContext(), mAppointmentList);
        mAppointmentListView.setAdapter(mAppointmentAdapter);

        //float button
        mAppointmentAddBtn.setOnClickListener(new View.OnClickListener() {
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

    // other functions

    // appointment booking
    private void initiateForm() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_appointment, null);
        dialogBuilder.setView(dialogView);

        // action view
        final Button bookBtn = dialogView.findViewById(R.id.form_book_btn);
        final FrameLayout progressForm = dialogView.findViewById(R.id.form_progressbar);
        final EditText specialist = dialogView.findViewById(R.id.form_specialist);
        final EditText doctor = dialogView.findViewById(R.id.form_doctors);
        dateET = dialogView.findViewById(R.id.form_date);
        final EditText note = dialogView.findViewById(R.id.form_note);

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Objects.requireNonNull(getActivity()), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookBtn.setVisibility(View.GONE);
                progressForm.setVisibility(View.VISIBLE);
                Appointment appointment = new Appointment();
                CreateNewAppointment(appointment);
            }
        });
        mDialogForm = dialogBuilder.create();
        mDialogForm.show();
    }

    // add new appointment
    private void CreateNewAppointment(Appointment appointment) {
        mDialogForm.dismiss();
        mAppointmentList.add(appointment);
        mAppointmentAdapter.updateAdapter(mAppointmentList);
    }

    private void updateLabel() {
        String myFormat = "dd MMMM yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateET.setText(sdf.format(myCalendar.getTime()));
    }

}
