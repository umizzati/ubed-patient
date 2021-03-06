package com.feka.ubed_patient.fragment.main_activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.feka.ubed_patient.BaseApplication;
import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.activity.BaseActivity;
import com.feka.ubed_patient.adapter.AppointmentAdapter;
import com.feka.ubed_patient.model.Appointment;
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
    DatePickerDialog.OnDateSetListener date, admindate;
    Calendar myCalendar;
    Calendar myCalendarTime;
    TimePickerDialog.OnTimeSetListener time, admintime;
    EditText dateET;
    EditText timeET;
    android.support.v7.widget.AppCompatSpinner doctorSpinner, specialistSpinner;
    User mCurrentUser;
    EditText adminDate, adminTime;

    private int mHour, mMinute;

    public AppointmentFragment() {
    }

    public interface OnFragmentInteractionListener {
        void onSuccessAddApp();
        void onFailedAddApp();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = BaseActivity.user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appoinment, container, false);
        mAppointmentListView = v.findViewById(R.id.appointment_listview);
        mAppointmentAddBtn = v.findViewById(R.id.appointment_add_btn);

        mAppointmentAdapter = new AppointmentAdapter(getContext(), mAppointmentList);
        mAppointmentListView.setAdapter(mAppointmentAdapter);

        TextView emptyTV = v.findViewById(R.id.appointment_empty);
        mAppointmentListView.setEmptyView(emptyTV);

        //listview clicklistner
        mAppointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment app = mAppointmentList.get(position);
                if (mCurrentUser.isAdmin()){
                    adminAction(app);
                }else{
                    patientAction(app);
                }
            }
        });

        //float button
        mAppointmentAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateForm();
            }
        });

        myCalendar = Calendar.getInstance();
        myCalendarTime = Calendar.getInstance();

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

        admindate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateAdminLabel();
            }

        };

        time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendarTime.set(Calendar.HOUR, hourOfDay);
                myCalendarTime.set(Calendar.MINUTE, minute);
                updateLabelTime();
            }
        };

        admintime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendarTime.set(Calendar.HOUR, hourOfDay);
                myCalendarTime.set(Calendar.MINUTE, minute);
                updateAdminTime();
            }
        };

        Query query;
        if (mCurrentUser.isAdmin()){
            query = BaseApplication.fireStoreDB.collection("appointments").whereEqualTo("deleted", false);
        }else{
            query = BaseApplication.fireStoreDB.collection("appointments").whereEqualTo("deleted", false).whereEqualTo("user_id", mCurrentUser.getUser_id());
        }

        updateAppointmentList(query);
        getAppointment(query);

        return v;
    }

    private void patientAction(final Appointment app) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder
                .setTitle(R.string.action_patient)
                .setPositiveButton(R.string.action_discard, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onDiscardBed(app);
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });


        mDialogForm = dialogBuilder.create();
        mDialogForm.show();
    }

    private void onDiscardBed(Appointment app) {
        BaseApplication.fireStoreDB
                .collection("appointments")
                .document(app.getId())
                .update("deleted", true);
        mDialogForm.dismiss();
    }

    private void adminAction(final Appointment app) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.action_app_admin, null);
        adminDate = dialogView.findViewById(R.id.admin_app_date);
        adminTime = dialogView.findViewById(R.id.admin_app_time);
        adminDate.setText(app.getDate());
        adminTime.setText(app.getTime());

        adminDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Objects.requireNonNull(getActivity()), admindate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        adminTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                new TimePickerDialog(getActivity(), admintime, mHour, mMinute, false).show();
            }
        });

        dialogBuilder.setView(dialogView);

        dialogBuilder
                .setTitle(R.string.action_admin)
                .setPositiveButton(R.string.action_approve, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onApproveBed(app);
                    }
                })
                .setNegativeButton(R.string.action_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onRejectBed(app);
                    }
                });


        mDialogForm = dialogBuilder.create();
        mDialogForm.show();
    }

    private void onRejectBed(Appointment app) {
        BaseApplication.fireStoreDB
                .collection("appointments")
                .document(app.getId())
                .update("status", Constant.BOOKING_REJECTED);
        mDialogForm.dismiss();
    }

    private void onApproveBed(Appointment app) {
        BaseApplication.fireStoreDB
                .collection("appointments")
                .document(app.getId())
                .update("status", Constant.BOOKING_APPROVED);
        mDialogForm.dismiss();
    }

    private void getAppointment(Query query) {
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(Objects.requireNonNull(task.getResult()).size() > 0){
                    mAppointmentList = (ArrayList<Appointment>) task.getResult().toObjects(Appointment.class);
                    int index = 0;
                    for(Appointment app : mAppointmentList){
                        app.setId(task.getResult().getDocuments().get(index).getId());
                        index++;
                    }
                    mAppointmentAdapter.updateAdapter(mAppointmentList);
                }
            }
        });
    }

    private void updateAppointmentList(Query query) {
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null) {
                    mAppointmentList = (ArrayList<Appointment>) snapshot.toObjects(Appointment.class);
                    int index = 0;
                    for(Appointment app : mAppointmentList){
                        app.setId((snapshot.getDocuments().get(index).getId()));
                        index++;
                    }
                    mAppointmentAdapter.updateAdapter(mAppointmentList);
                }
            }
        });
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
        specialistSpinner = dialogView.findViewById(R.id.form_specialist);
        doctorSpinner = dialogView.findViewById(R.id.form_doctors);
        dateET = dialogView.findViewById(R.id.form_date);
        timeET = dialogView.findViewById(R.id.form_time);
        final EditText note = dialogView.findViewById(R.id.form_note);

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Objects.requireNonNull(getActivity()), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                new TimePickerDialog(getActivity(), time, mHour, mMinute, false).show();
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookBtn.setVisibility(View.GONE);
                progressForm.setVisibility(View.VISIBLE);
                Appointment appointment = new Appointment(mCurrentUser.getName(), mCurrentUser.getUser_id(), specialistSpinner.getSelectedItem().toString(), doctorSpinner.getSelectedItem().toString(), note.getText().toString(), dateET.getText().toString(), timeET.getText().toString());
                CreateNewAppointment(appointment);
            }
        });
        mDialogForm = dialogBuilder.create();
        mDialogForm.show();
    }

    // add new appointment
    private void CreateNewAppointment(Appointment appointment) {
        BaseApplication.fireStoreDB.collection("appointments")
                .add(appointment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mDialogForm.dismiss();
                        mListener.onSuccessAddApp();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mListener.onFailedAddApp();
                    }
                });
    }

    private void updateLabel() {
        String myFormat = "EEE, dd MMM yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateET.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateAdminLabel() {
        String myFormat = "EEE, dd MMM yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        adminDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelTime() {
        String myFormat = "h.mm a"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        timeET.setText(sdf.format(myCalendarTime.getTime()));
    }

    private void updateAdminTime() {
        String myFormat = "h.mm a"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        adminTime.setText(sdf.format(myCalendarTime.getTime()));
    }

}
