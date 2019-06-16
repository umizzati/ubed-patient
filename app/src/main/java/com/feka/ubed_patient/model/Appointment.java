package com.feka.ubed_patient.model;

import com.feka.ubed_patient.Constant;

public class Appointment {

    private String user_name;
    private String user_id;
    private String status;
    private String specialist;
    private String doctor;
    private String note;
    private String date;
    private String time;

    public Appointment(String user_name, String user_id, String specialist, String doctor, String note, String date, String time) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.specialist = specialist;
        this.doctor = doctor;
        this.note = note;
        this.date = date;
        this.time = time;
        this.status = Constant.BOOKING_PENDING;
    }

    public Appointment() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }
}
