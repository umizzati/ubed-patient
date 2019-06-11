package com.feka.ubed_patient.model;

import com.feka.ubed_patient.Constant;

public class Appointment {

    private String specialist;
    private String status;
    private String doctor;
    private String note;
    private long check_in;
    private long check_out;
    private boolean isRemove;
    private long created_time;
    private long updated_time;

    public Appointment() {
        this.status = Constant.BOOKING_PENDING;
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

    public long getCheck_in() {
        return check_in;
    }

    public void setCheck_in(long check_in) {
        this.check_in = check_in;
    }

    public long getCheck_out() {
        return check_out;
    }

    public void setCheck_out(long check_out) {
        this.check_out = check_out;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    public long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public long getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(long updated_time) {
        this.updated_time = updated_time;
    }


}
