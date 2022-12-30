package com.midhun.hawkssolutions.aryaas.View;

public class Images {
    private String id;

    public Images(String id, String product, String image, String status) {
        this.id = id;
        this.product = product;
        this.image = image;
        this.status = status;
    }

    private String product;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

}
