package com.feka.ubed_patient.model;

import com.feka.ubed_patient.Constant;

public class Bed {
    private String id;
    private String patient_id;
    private String user_id;
    private String user_name;
    private String bed_name;
    private String bed_type;
    private String specialist;
    private String doctor;
    private String status;
    private String note;
    private String check_in;
    private String check_out;
    private boolean deleted;

    public Bed(String user_name, String user_id, String patient_id, String bed_type, String specialist, String doctor, String check_in, String note) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.patient_id = patient_id;
        this.bed_type = bed_type;
        this.specialist = specialist;
        this.doctor = doctor;
        this.note = note;
        this.check_in = check_in;
        this.status = Constant.BOOKING_PENDING;
        this.deleted = false;
    }

    public Bed() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBed_name() {
        return bed_name;
    }

    public void setBed_name(String bed_name) {
        this.bed_name = bed_name;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCheck_in() {
        return check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getBed_type() {
        return bed_type;
    }

    public void setBed_type(String bed_type) {
        this.bed_type = bed_type;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
