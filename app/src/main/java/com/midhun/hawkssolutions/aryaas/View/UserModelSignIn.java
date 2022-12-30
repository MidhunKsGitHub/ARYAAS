package com.midhun.hawkssolutions.aryaas.View;

public class UserModelSignIn {
    private String id;
    private String username;
    private String password;
    private String name;
    private String usertype;
    private String created_by;
    private String created_date;
    private String modified_date;
    private String type;
    private String error;
    private String  user_type;

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public UserModelSignIn(String id, String username, String password, String name, String usertype, String created_by, String created_date, String modified_date, String status, String rememberme, String type, String user_type, String error) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.usertype = usertype;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_date = modified_date;
        this.status = status;
        this.rememberme = rememberme;
        this.type=type;
        this.user_type=user_type;
        this.error=error;
    }

    private String status;
    private String rememberme;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRememberme() {
        return rememberme;
    }

    public void setRememberme(String rememberme) {
        this.rememberme = rememberme;
    }
}
