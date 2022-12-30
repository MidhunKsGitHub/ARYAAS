package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.FavModel;

import java.util.List;

public class FavApiModel {

    FavList pagination;

    public FavList getPagination() {
        return pagination;
    }

    public void setPagination(FavList pagination) {
        this.pagination = pagination;
    }

    public FavApiModel(FavList pagination) {
        this.pagination = pagination;
    }

    public List<FavModel> FavList(){
        return getPagination().pageData;
    }

    class FavList{
        List<FavModel> pageData;

        public List<FavModel> getPageData() {
            return pageData;
        }

        public void setPageData(List<FavModel> pageData) {
            this.pageData = pageData;
        }

        public FavList(List<FavModel> pageData) {
            this.pageData = pageData;
        }
    }
}
