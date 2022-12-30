package com.midhun.hawkssolutions.aryaas.View;

import com.google.gson.annotations.SerializedName;

public class Cart {
    private String id;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("user_id")
    private String userId;
    private String product;
    private String price;
    private String quantity;
    private String tax;
    private String date;
    private String model;
    private String thumb;
    private String image;
    private String stone_amount;

    public String getCombinationSize() {
        return combinationSize;
    }

    public void setCombinationSize(String combinationSize) {
        this.combinationSize = combinationSize;
    }

    public Cart(String id, String productId, String userId, String product, String price, String quantity, String tax, String date, String model, String thumb, String image, String stone_amount, String size, String combinationSize, String color, String tax_id) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.tax = tax;
        this.date = date;
        this.model = model;
        this.thumb = thumb;
        this.image = image;
        this.stone_amount = stone_amount;
        this.size = size;
        this.color = color;
        this.tax_id = tax_id;
        this.combinationSize=combinationSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStone_amount() {
        return stone_amount;
    }

    public void setStone_amount(String stone_amount) {
        this.stone_amount = stone_amount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }


    private String size;
    private String combinationSize;
    private String color;
    private String tax_id;
}
