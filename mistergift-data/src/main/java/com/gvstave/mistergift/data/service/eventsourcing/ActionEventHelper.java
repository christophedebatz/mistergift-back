package com.gvstave.mistergift.data.service.eventsourcing;

public class ActionEventHelper {

	public static UserActionHelper user() {
		return new UserActionHelper();
	}

	public static class UserActionHelper {

		public ActionEventType subscribed() {
			return ActionEventType.SUBSCRIBED;
		}

		public ActionEventType removed() {
			return ActionEventType.REMOVED;
		}

		public ActionEventType updated() {
			return ActionEventType.UPDATED;
		}

	}

}
