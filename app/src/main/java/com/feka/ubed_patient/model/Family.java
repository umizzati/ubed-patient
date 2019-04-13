package com.feka.ubed_patient.model;

class Family {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel_num() {
        return tel_num;
    }

    public void setTel_num(String tel_num) {
        this.tel_num = tel_num;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Family(String name, String address, String tel_num, String relation) {
        this.name = name;
        this.address = address;
        this.tel_num = tel_num;
        this.relation = relation;
    }

    public String name;
    public String address;
    public String tel_num;
    public String relation;
}
