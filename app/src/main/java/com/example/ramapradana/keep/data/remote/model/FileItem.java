package com.example.ramapradana.keep.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FileItem implements Serializable {

	@SerializedName("eventfile_id")
	private int eventfileId;

	@SerializedName("event_id")
	private int eventId;

	@SerializedName("eventfile_title")
	private String eventfileTitle;

	@SerializedName("eventfile_content")
	private String eventfileContent;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("upload_by")
	private int uploadBy;

	@SerializedName("eventfile_format")
	private String eventfileFormat;

	public void setEventfileId(int eventfileId){
		this.eventfileId = eventfileId;
	}

	public int getEventfileId(){
		return eventfileId;
	}

	public void setEventId(int eventId){
		this.eventId = eventId;
	}

	public int getEventId(){
		return eventId;
	}

	public void setEventfileTitle(String eventfileTitle){
		this.eventfileTitle = eventfileTitle;
	}

	public String getEventfileTitle(){
		return eventfileTitle;
	}

	public void setEventfileContent(String eventfileContent){
		this.eventfileContent = eventfileContent;
	}

	public String getEventfileContent(){
		return eventfileContent;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setUploadBy(int uploadBy){
		this.uploadBy = uploadBy;
	}

	public int getUploadBy(){
		return uploadBy;
	}

	public void setEventfileFormat(String eventfileFormat){
		this.eventfileFormat = eventfileFormat;
	}

	public String getEventfileFormat(){
		return eventfileFormat;
	}

	@Override
 	public String toString(){
		return 
			"FileItem{" + 
			"eventfile_id = '" + eventfileId + '\'' + 
			",event_id = '" + eventId + '\'' + 
			",eventfile_title = '" + eventfileTitle + '\'' + 
			",eventfile_content = '" + eventfileContent + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",upload_by = '" + uploadBy + '\'' + 
			",eventfile_format = '" + eventfileFormat + '\'' + 
			"}";
		}

}