package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.Profile;

import java.util.List;

public class ProfileApiModel {
    public ProfileApiModel(List<Profile> data) {
        this.data = data;
    }

    public List<Profile> getData() {
        return data;
    }

    public void setData(List<Profile> data) {
        this.data = data;
    }

    List<Profile> data;

}
