package com.midhun.hawkssolutions.aryaas.View;

public class Related {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCombinationid() {
        return combinationid;
    }

    public void setCombinationid(String combinationid) {
        this.combinationid = combinationid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Related(String name, String price, String image,String id,String combinationid,String size) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.combinationid=combinationid;
        this.id=id;
        this.size=size;
    }

    String name,price,image,size,combinationid,id;
}
