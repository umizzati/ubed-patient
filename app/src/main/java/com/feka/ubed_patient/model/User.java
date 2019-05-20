package com.feka.ubed_patient.model;

public class User {

    private String name;
    private String email;
    private String password;
    private String tel_num;
    private String ic_num;
    private boolean isPatient;
    private boolean isActive;
    private long created;
    private long updated;


    public User(String name, String email, String tel_num, String ic_num, String password, boolean isPatient, boolean isActive, long created, long updated) {
        this.name = name;
        this.email = email;
        this.tel_num = tel_num;
        this.ic_num = ic_num;
        this.password = password;
        this.isPatient = isPatient;
        this.isActive = isActive;
        this.created = created;
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel_num() {
        return tel_num;
    }

    public void setTel_num(String tel_num) {
        this.tel_num = tel_num;
    }

    public String getIc_num() {
        return ic_num;
    }

    public void setIc_num(String ic_num) {
        this.ic_num = ic_num;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPatient() {
        return isPatient;
    }

    public void setPatient(boolean patient) {
        isPatient = patient;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }
}
