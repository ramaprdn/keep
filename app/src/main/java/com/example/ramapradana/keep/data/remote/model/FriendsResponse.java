package com.example.ramapradana.keep.data.remote.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FriendsResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("user")
	private List<UserItem> user;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setUser(List<UserItem> user){
		this.user = user;
	}

	public List<UserItem> getUser(){
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
			"FriendsResponse{" + 
			"msg = '" + msg + '\'' + 
			",user = '" + user + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}