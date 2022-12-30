package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.Result;

import java.util.List;

public class ProductDataApiModel {
    List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public ProductDataApiModel(List<Result> result) {
        this.result = result;
    }


}
