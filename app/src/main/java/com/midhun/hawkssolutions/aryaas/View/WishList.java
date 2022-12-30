package com.midhun.hawkssolutions.aryaas.View;

public class WishList {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String status;

    public WishList(String status, String id) {
        this.status = status;
        this.id = id;
    }

    private String id;
}
