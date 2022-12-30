package com.midhun.hawkssolutions.aryaas.View;

public class ProductSize {

    private String id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ProductSize(String id, String combination, String product, String attribute, String attributeValue, String priceAttribute, String price, String groupname,String name) {
        this.id = id;
        this.combination = combination;
        this.product = product;
        this.attribute = attribute;
        this.attributeValue = attributeValue;
        this.priceAttribute = priceAttribute;
        this.groupname = groupname;
        this.price=price;
        this.name=name;
    }

    private String combination;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCombination() {
        return combination;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getPriceAttribute() {
        return priceAttribute;
    }

    public void setPriceAttribute(String priceAttribute) {
        this.priceAttribute = priceAttribute;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    private String product;
    private String attribute;
    private String attributeValue;
    private String priceAttribute;
    private String groupname;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
