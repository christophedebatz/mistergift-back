package com.gvstave.mistergift.data.service.eventsourcing;

public enum ActionEventType {

	SUBSCRIBED("subscribed"),

	REMOVED("removed"),

	UPDATED("updated");

	String key;

	ActionEventType (String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}