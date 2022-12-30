package com.midhun.hawkssolutions.aryaas.View;

public class GetMyOrder {
    private String customer;
    private String date;
    private String id;
    private String total;
    private String status;
    private String image;
    private String status_note;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_note() {
        return status_note;
    }

    public void setStatus_note(String status_note) {
        this.status_note = status_note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public GetMyOrder(String customer, String date, String id, String total, String name, String phone, String address, String cartName, String status, String status_note, String image) {
        this.customer = customer;
        this.date = date;
        this.id = id;
        this.total = total;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.cartName = cartName;
        this.status=status;
        this.status_note=status_note;
        this.image=image;
    }

    private String name;
    private String phone;
    private String address;
    private String cartName;
}
