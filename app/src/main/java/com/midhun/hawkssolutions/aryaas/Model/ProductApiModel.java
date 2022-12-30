package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.Products;

import java.util.List;

public class ProductApiModel {

   private ProductsList pagination;

    public ProductApiModel(ProductsList pagination) {
        this.pagination = pagination;
    }
    public List<Products> PList(){
        return getPagination().pageData;
    }
    public ProductsList getPagination() {
        return pagination;
    }

    public void setPagination(ProductsList pagination) {
        this.pagination = pagination;
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
