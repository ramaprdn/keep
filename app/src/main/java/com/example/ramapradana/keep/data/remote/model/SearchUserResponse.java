package com.example.ramapradana.keep.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class SearchUserResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("user")
	private User user;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"SearchUserResponse{" + 
			"msg = '" + msg + '\'' + 
			",user = '" + user + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}