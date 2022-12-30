package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.GetMyOrder;

import java.util.List;

public class GetMyOrderApiModel {
    GetMyOderList data;

    public GetMyOderList getData() {
        return data;
    }

    public void setData(GetMyOderList data) {
        this.data = data;
    }

    public GetMyOrderApiModel(GetMyOderList data) {
        this.data = data;
    }
    public List<GetMyOrder> Olist(){
        return getData().pageData;
    }

    class GetMyOderList {
       List<GetMyOrder> pageData;

        public List<GetMyOrder> getPageData() {
            return pageData;
        }

        public void setPageData(List<GetMyOrder> pageData) {
            this.pageData = pageData;
        }

        public GetMyOderList(List<GetMyOrder> pageData) {
            this.pageData = pageData;
        }
    }
}
