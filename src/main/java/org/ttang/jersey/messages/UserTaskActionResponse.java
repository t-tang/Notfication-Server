package org.ttang.jersey.messages;

import javax.ws.rs.core.Response;

import org.ttang.UserTask;

public class UserTaskActionResponse {
	private String userTaskId;
	private String actionId;
	private String userStatusMessage;

	public String getUserTaskId() {
		return userTaskId;
	}

	public String getActionId() {
		return actionId;
	}

	public String getUserStatusMessage() {
		return userStatusMessage;
	}

	private UserTaskActionResponse(String userTaskId, String actionId,
			String userStatusMessage) {
		this.userTaskId = userTaskId;
		this.actionId = actionId;
		this.userStatusMessage = userStatusMessage;
	}

	public static Response taskNotFound(String userTaskId, String actionId) {
		return Response.ok(Ember.wrap(new UserTaskActionResponse(userTaskId, actionId, "Task not found"))).build();

	}

	public static Response actionNotFound(String userTaskId, String actionId) {
		return Response.ok(Ember.wrap(new UserTaskActionResponse(userTaskId, actionId, "Action not found"))).build();
	}

	public static Response userTask(UserTask userTask) {
		return Response.ok(Ember.wrap(userTask)).build();

	}

	public static Response serverError(String userTaskId, String actionId) {
		return Response.ok(Ember.wrap(new UserTaskActionResponse(userTaskId, actionId, "An internal server error occurred"))).build();
	}
}
