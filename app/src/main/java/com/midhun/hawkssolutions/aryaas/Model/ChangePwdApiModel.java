package com.midhun.hawkssolutions.aryaas.Model;

import com.midhun.hawkssolutions.aryaas.View.ChangePwd;

public class ChangePwdApiModel {
    public ChangePwd getStatus() {
        return status;
    }

    public void setStatus(ChangePwd status) {
        this.status = status;
    }

    ChangePwd status;

    public ChangePwdApiModel(ChangePwd status) {
        this.status = status;
    }
}
