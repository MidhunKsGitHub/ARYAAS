package com.midhun.hawkssolutions.aryaas.View;

public class UserModelSignUP {
    private String status;
    private String userid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserModelSignUP(String status, String userid, String username) {
        this.status = status;
        this.userid = userid;
        this.username = username;
    }

    private String username;
}
