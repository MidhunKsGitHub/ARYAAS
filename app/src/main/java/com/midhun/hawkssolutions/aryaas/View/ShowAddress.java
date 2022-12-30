package com.midhun.hawkssolutions.aryaas.View;

public class ShowAddress {
    private String id;
    private String user_id;
    private String address;
    private String pincode;
    private String name;
    private String country;
    private String state;

    public ShowAddress(String id, String user_id, String address, String pincode, String name, String country, String state, String city, String setdefault, String phone, String status, String deleteStatus) {
        this.id = id;
        this.user_id = user_id;
        this.address = address;
        this.pincode = pincode;
        this.name = name;
        this.country = country;
        this.state = state;
        this.city = city;
        this.setdefault = setdefault;
        this.phone = phone;
        this.status = status;
        this.deleteStatus = deleteStatus;
    }

    private String city;
    private String setdefault;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSetdefault() {
        return setdefault;
    }

    public void setSetdefault(String setdefault) {
        this.setdefault = setdefault;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    private String phone;
    private String status;
    private String deleteStatus;
}
