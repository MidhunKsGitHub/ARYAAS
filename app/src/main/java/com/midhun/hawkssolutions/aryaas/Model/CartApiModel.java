package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.Cart;

import java.util.List;

public class CartApiModel {
    List<Cart> cart;

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public CartApiModel(List<Cart> cart) {
        this.cart = cart;
    }
}
