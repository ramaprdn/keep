package com.example.ramapradana.keep.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("user_id")
	private int userId;

	@SerializedName("user_username")
	private String userUsername;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("user_email")
	private String userEmail;

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setUserUsername(String userUsername){
		this.userUsername = userUsername;
	}

	public String getUserUsername(){
		return userUsername;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"user_id = '" + userId + '\'' + 
			",user_username = '" + userUsername + '\'' + 
			",user_name = '" + userName + '\'' + 
			"}";
		}
}