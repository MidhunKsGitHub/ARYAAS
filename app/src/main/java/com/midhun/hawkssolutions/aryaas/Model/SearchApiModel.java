package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.Products;

import java.util.List;

public class SearchApiModel {
    private ProductsList data;

    public ProductsList getData() {
        return data;
    }

    public void setData(ProductsList data) {
        this.data = data;
    }
    public List<Products> PList(){
        return getData().pageData;
    }

    public SearchApiModel(ProductsList data) {
        this.data = data;
    }

    class ProductsList{
        private  List<Products> pageData;

        public List<Products> getPageData() {
            return pageData;
        }

        public void setPageData(List<Products> pageData) {
            this.pageData = pageData;
        }

        public ProductsList(List<Products> pageData) {
            this.pageData = pageData;
        }
    }
}
