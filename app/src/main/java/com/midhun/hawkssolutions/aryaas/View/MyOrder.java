package com.midhun.hawkssolutions.aryaas.View;

public class MyOrder {
    private String id;
    private String customer;
    private String date;
    private String total;
    private String address;
    private String status;
    private String paid;
    private String reason;
    private String statusChangReason;
    private String deleteStatus;
    private String placedStatus;
    private String name;
    private String phone;
    private String product;
    private String quantity;
    private String size;
    private String price;
    private String image;
    private String delivery_person;

    private String delivery_out_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatusChangReason() {
        return statusChangReason;
    }

    public void setStatusChangReason(String statusChangReason) {
        this.statusChangReason = statusChangReason;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getPlacedStatus() {
        return placedStatus;
    }

    public void setPlacedStatus(String placedStatus) {
        this.placedStatus = placedStatus;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private String thumb;

    public String getDelivery_person() {
        return delivery_person;
    }

    public void setDelivery_person(String delivery_person) {
        this.delivery_person = delivery_person;
    }

    public String getDelivery_out_at() {
        return delivery_out_at;
    }

    public void setDelivery_out_at(String delivery_out_at) {
        this.delivery_out_at = delivery_out_at;
    }

    public MyOrder(String id, String customer, String date, String total, String address, String status, String paid, String reason, String statusChangReason, String deleteStatus, String placedStatus, String name, String phone, String product, String quantity, String size, String price, String image, String thumb, String model, String delivery_out_at, String delivery_person) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.total = total;
        this.address = address;
        this.status = status;
        this.paid = paid;
        this.reason = reason;
        this.statusChangReason = statusChangReason;
        this.deleteStatus = deleteStatus;
        this.placedStatus = placedStatus;
        this.name = name;
        this.phone = phone;
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
        this.image = image;
        this.thumb = thumb;
        this.model = model;
        this.delivery_out_at=delivery_out_at;
        this.delivery_person=delivery_person;
    }

    private String model;
}
