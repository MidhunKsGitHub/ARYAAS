package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.ProductSize;

import java.util.List;

public class ProductSizeApiModel {
   List<ProductSize> productSize;

    public List<ProductSize> getProductSize() {
        return productSize;
    }

    public void setProductSize(List<ProductSize> productSize) {
        this.productSize = productSize;
    }

    public ProductSizeApiModel(List<ProductSize> productSize) {
        this.productSize = productSize;
    }
}
