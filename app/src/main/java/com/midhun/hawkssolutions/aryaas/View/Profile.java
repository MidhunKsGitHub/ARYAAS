package com.midhun.hawkssolutions.aryaas.View;

public class Profile {
    private String id;
    private String first_name;

    public Profile(String id, String first_name, String last_name, String image, String email, String phone, String dob, String newsletter, String status) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.image = image;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.newsletter = newsletter;
        this.status = status;
    }

    private String last_name;
    private String image;
    private String email;
    private String phone;
    private String dob;
    private String newsletter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
}
