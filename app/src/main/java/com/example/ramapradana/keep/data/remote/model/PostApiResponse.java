package com.example.ramapradana.keep.data.remote.model;

public class PostApiResponse {

    /**
     * status : false
     * msg : Username has been used by other person.
     */

    private boolean status;
    private String msg;

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
}
