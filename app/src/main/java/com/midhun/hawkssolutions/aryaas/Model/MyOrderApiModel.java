package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.MyOrder;

import java.util.List;

public class MyOrderApiModel {

    MyOrderList data;

    public MyOrderList getData() {
        return data;
    }

    public void setData(MyOrderList data) {
        this.data = data;
    }

    public MyOrderApiModel(MyOrderList data) {
        this.data = data;
    }

    public List<MyOrder> OList(){
        return getData().pageData;
    }

    class MyOrderList{
        List<MyOrder> pageData;

        public MyOrderList(List<MyOrder> pageData) {
            this.pageData = pageData;
        }

        public List<MyOrder> getPageData() {
            return pageData;
        }

        public void setPageData(List<MyOrder> pageData) {
            this.pageData = pageData;
        }
    }
}
