package com.example.ramapradana.keep.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EventFileResponse implements Parcelable {

	@SerializedName("msg")
	private String msg;

	@SerializedName("file")
	private List<FileItem> file;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setFile(List<FileItem> file){
		this.file = file;
	}

	public List<FileItem> getFile(){
		return file;
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
			"EventFileResponse{" + 
			"msg = '" + msg + '\'' + 
			",file = '" + file + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
}