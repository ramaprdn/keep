package com.example.ramapradana.keep.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class CreateNoteResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("uploadBy")
	private int uploadBy;

	@SerializedName("createdAt")
	private CreatedAt createdAt;

	@SerializedName("format")
	private String format;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setUploadBy(int uploadBy){
		this.uploadBy = uploadBy;
	}

	public int getUploadBy(){
		return uploadBy;
	}

	public void setCreatedAt(CreatedAt createdAt){
		this.createdAt = createdAt;
	}

	public CreatedAt getCreatedAt(){
		return createdAt;
	}

	public void setFormat(String format){
		this.format = format;
	}

	public String getFormat(){
		return format;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
			"CreateNoteResponse{" + 
			"msg = '" + msg + '\'' + 
			",uploadBy = '" + uploadBy + '\'' + 
			",createdAt = '" + createdAt + '\'' + 
			",format = '" + format + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}