package com.example.ramapradana.keep.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventsItem implements Serializable {

	@SerializedName("event_id")
	private int eventId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("event_file_count")
	private int eventFileCount;

	@SerializedName("event_name")
	private String eventName;

	@SerializedName("created_at")
	private String createdAt;


	public void setEventId(int eventId){
		this.eventId = eventId;
	}

	public int getEventId(){
		return eventId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setEventFileCount(int eventFileCount){
		this.eventFileCount = eventFileCount;
	}

	public int getEventFileCount(){
		return eventFileCount;
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

	@Override
 	public String toString(){
		return 
			"EventsItem{" + 
			"event_id = '" + eventId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",event_file_count = '" + eventFileCount + '\'' + 
			",event_name = '" + eventName + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			"}";
		}

}