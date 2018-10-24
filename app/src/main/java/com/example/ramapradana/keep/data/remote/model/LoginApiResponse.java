package com.example.ramapradana.keep.data.remote.model;

public class LoginApiResponse {

    /**
     * status : true
     * msg : User found. Login success.
     * user_name : Rama Pradana
     * user_username : ramapradana
     * user_email : ramapradana@gmail.com
     * user_access_token : $2y$10$Ys2Cd/eRP8iGDzSZvOy4v.6TF6mH4sAJfp7aalP4G5vctk3MNz9km
     */

    private boolean status;
    private String msg;
    private String user_name;
    private String user_username;
    private String user_email;
    private String user_access_token;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_access_token() {
        return user_access_token;
    }

    public void setUser_access_token(String user_access_token) {
        this.user_access_token = user_access_token;
    }
}
