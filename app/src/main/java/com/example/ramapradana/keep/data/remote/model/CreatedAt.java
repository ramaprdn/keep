package com.example.ramapradana.keep.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class CreatedAt{

	@SerializedName("date")
	private String date;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("timezone_type")
	private int timezoneType;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setTimezone(String timezone){
		this.timezone = timezone;
	}

	public String getTimezone(){
		return timezone;
	}

	public void setTimezoneType(int timezoneType){
		this.timezoneType = timezoneType;
	}

	public int getTimezoneType(){
		return timezoneType;
	}

	@Override
 	public String toString(){
		return 
			"CreatedAt{" + 
			"date = '" + date + '\'' + 
			",timezone = '" + timezone + '\'' + 
			",timezone_type = '" + timezoneType + '\'' + 
			"}";
		}
}