package com.feka.ubed_patient.model;

class Profile {
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNo_ic() {
        return no_ic;
    }

    public void setNo_ic(int no_ic) {
        this.no_ic = no_ic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTel_number() {
        return tel_number;
    }

    public void setTel_number(int tel_number) {
        this.tel_number = tel_number;
    }

    public boolean isMalaysian() {
        return isMalaysian;
    }

    public void setMalaysian(boolean malaysian) {
        isMalaysian = malaysian;
    }


    public Profile(String fullName, int no_ic, String address, int tel_number, boolean isMalaysian) {
        this.fullName = fullName;
        this.no_ic = no_ic;
        this.address = address;
        this.tel_number = tel_number;
        this.isMalaysian = isMalaysian;
    }

    public String fullName;
    public int no_ic;
    public String address;
    public int tel_number;
    public boolean isMalaysian = true;
}
