package com.midhun.hawkssolutions.aryaas.Model;


import com.midhun.hawkssolutions.aryaas.View.CategoryProducts;

import java.util.List;

public class CategoryProductsApiModel {
    CategoryProductData data;

    public CategoryProductsApiModel(CategoryProductData data) {
        this.data = data;
    }
public List<CategoryProducts> CPList(){
        return getData().categoryProductsList;
    }
    public CategoryProductData getData() {
        return data;
    }

    public void setData(CategoryProductData data) {
        this.data = data;
    }

    class CategoryProductData {
        List<CategoryProducts> categoryProductsList;

        public CategoryProductData(List<CategoryProducts> categoryProductsList) {
            this.categoryProductsList = categoryProductsList;
        }

        public List<CategoryProducts> getCategoryProductsList() {
            return categoryProductsList;
        }

        public void setCategoryProductsList(List<CategoryProducts> categoryProductsList) {
            this.categoryProductsList = categoryProductsList;
        }
    }
}
