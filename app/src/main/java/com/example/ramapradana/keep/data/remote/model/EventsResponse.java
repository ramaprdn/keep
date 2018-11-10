package com.example.ramapradana.keep.data.remote.model;

import java.util.List;

public class EventsResponse {
	private List<EventsItem> events;
	private boolean status;

	public void setEvents(List<EventsItem> events){
		this.events = events;
	}

	public List<EventsItem> getEvents(){
		return events;
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
			"EventsResponse{" +
			"events = '" + events + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}