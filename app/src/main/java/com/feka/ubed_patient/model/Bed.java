package com.feka.ubed_patient.model;

import com.feka.ubed_patient.Constant;

public class Bed {
    private int id;
    private String bed_id;
    private String patient_id;
    private String specialist;
    private String status;
    private String note;
    private long check_in;
    private long check_out;
    private boolean isRemove;
    private long created_time;
    private long updated_time;

    public Bed() {
//        this.id = id;
//        this.patient_id = patient_id;
//        this.specialist = specialist;
//        this.note = note;
//        this.check_in = check_in;
        this.created_time = System.currentTimeMillis()/1000;
        this.updated_time = System.currentTimeMillis()/1000;
        this.status = Constant.BOOKING_PENDING;
        this.isRemove = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBed_id() {
        return bed_id;
    }

    public void setBed_id(String bed_id) {
        this.bed_id = bed_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
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
