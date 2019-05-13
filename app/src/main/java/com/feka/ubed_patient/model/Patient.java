package com.feka.ubed_patient.model;

public class Patient {

    public Patient(String email, String encrypt_password, String token, boolean isActive, Medical medical, Profile profile, Family[] family, String updated, String created) {
        this.email = email;
        this.encrypt_password = encrypt_password;
        this.token = token;
        this.isActive = isActive;
        this.medical = medical;
        this.profile = profile;
        this.family = family;
        this.updated = updated;
        this.created = created;
    }

    public String email;
    public String encrypt_password;
    public String token;
    public boolean isActive;
    public Medical medical;
    public Profile profile;
    public Family[] family;
    public String updated;
    public String created;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncrypt_password() {
        return encrypt_password;
    }

    public void setEncrypt_password(String encrypt_password) {
        this.encrypt_password = encrypt_password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Medical getMedical() {
        return medical;
    }

    public void setMedical(Medical medical) {
        this.medical = medical;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Family[] getFamily() {
        return family;
    }

    public void setFamily(Family[] family) {
        this.family = family;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
