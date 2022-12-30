package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.ShowAddress;

import java.util.List;

public class ShowAddressApiModel {
    List<ShowAddress> status;

    public ShowAddressApiModel(List<ShowAddress> status) {
        this.status = status;
    }

    public List<ShowAddress> getStatus() {
        return status;
    }

    public void setStatus(List<ShowAddress> status) {
        this.status = status;
    }
}
