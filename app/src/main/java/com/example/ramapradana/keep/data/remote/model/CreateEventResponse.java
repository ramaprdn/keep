package com.example.ramapradana.keep.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class CreateEventResponse {

	@SerializedName("msg")
	private String msg;

	@SerializedName("file_count")
	private int fileCount;

	@SerializedName("event_id")
	private int eventId;

	@SerializedName("event_name")
	private String eventName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setFileCount(int fileCount){
		this.fileCount = fileCount;
	}

	public int getFileCount(){
		return fileCount;
	}

	public void setEventId(int eventId){
		this.eventId = eventId;
	}

	public int getEventId(){
		return eventId;
	}

	public void setEventName(String eventName){
		this.eventName = eventName;
	}

	public String getEventName(){
		return eventName;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
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
			"CreateEventResponse{" +
			"msg = '" + msg + '\'' + 
			",file_count = '" + fileCount + '\'' + 
			",event_id = '" + eventId + '\'' + 
			",event_name = '" + eventName + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}