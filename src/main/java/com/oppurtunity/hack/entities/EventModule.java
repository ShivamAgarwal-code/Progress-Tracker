package com.oppurtunity.hack.entities;

public class EventModule {
	private String eventid;
	private String eventlabel;
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public String getEventlabel() {
		return eventlabel;
	}
	public void setEventlabel(String eventlabel) {
		this.eventlabel = eventlabel;
	}

	@Override
	public String toString() {
		return "Module{" +
				"id='" + eventid + '\'' +
				", label='" + eventlabel + '\'' +
				'}';
	}

}
