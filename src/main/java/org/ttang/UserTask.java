package org.ttang;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.exception.ContextedException;
import org.ttang.jersey.messages.MessageType;
import org.ttang.objectstore.ObjectStoreItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@MessageType(name="UserTask")
public class UserTask extends ObjectStoreItem<String> {
	
	// OUTSTANDING = waiting for user to trigger an action
	// ACTIONED = waiting for the result of the action (primarily for async result)
	// COMPLETED = completed, the end state for this user task
	// For a synchronous operation, the ui may not see the ACTIONED state.
	private enum Status { OUTSTANDING, ACTIONED, COMPLETED }

	private String id;
	private Date creationTime;

	private String title;
	private String userMessage;
	private List<UserAction> actions;

	private Status status;
	private String userStatusMessage;

	private String action;
	private String actionedBy;
	private Date actionedTime;
	private Date completionTime;
	
	public static Builder builder(String title, String userMessage) {
		return new Builder(title, userMessage);
	}

	/* Create an in memory representation of a UserTask */
	private UserTask(String id, Date creationTime,
			String title, String userMessage, List<UserAction> actions,
			Status status, String userStatusMessage,
			String action, String actionedBy, Date actionedTime, Date completionTime) {
		this.id = id;
		this.creationTime = creationTime;
		this.title = title;
		this.userMessage = userMessage;
		this.actions = actions;
		this.status = status;
		this.userStatusMessage = userStatusMessage;

		this.action = action;
		this.actionedBy = actionedBy;
		this.actionedTime = actionedTime;
		this.completionTime = completionTime;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTask other = (UserTask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Date getCreationTime() {
		return creationTime;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public UserAction getAction(String actionId) {

		for (UserAction action : actions) {
			if (action.getId().equals(actionId)) {
				return action;
			}
		}
		
		return null;
	}
	
	/*
	 * For Json deserialization
	 */
	@JsonProperty
	private List<UserAction> getActions() {
		return actions;
	}
	
	public String getUserStatusMessage() {
		return userStatusMessage;
	}

	@JsonIgnore
	public boolean isOutstanding() {
		return status == Status.OUTSTANDING;
	}

	/**
	public boolean isActioned() {
		return status == Status.ACTIONED;
	}

	public boolean isCompleted() {
		return status == Status.COMPLETED;
	}
	**/
	
	public Status getStatus() {
		return status;
	}
	
	public String getAction() {
		return action;
	}

	public String getActionedBy() {
		return actionedBy;
	}

	public Date getActionedTime() {
		return actionedTime;
	}
	
	public Date getCompletionTime() {
		return completionTime;
	}
	
	public UserTask action(String action, String actionedBy)
			throws ContextedException {

		if (status != Status.OUTSTANDING) {
			throw new ContextedException("Expected status==OUTSTANDING")
				.addContextValue("this", this);
		}

		status = Status.ACTIONED;
		actionedTime = new Date();
		this.actionedBy = actionedBy;

		userStatusMessage = String.format("Actioned at %1$tH:%1$tM by %2$s", actionedTime, actionedBy);
		
		return this; // for chaining into complete
	}
	
	public void complete() throws ContextedException {
		if (status != Status.ACTIONED) {
			throw new ContextedException("Expected status==ACTIONED")
				.addContextValue("this", this);
		}

		// set completion information
		status = Status.COMPLETED;
		completionTime = new Date();

		userStatusMessage = String.format("Completed at %1$tH:%1$tM by %2$s", completionTime, actionedBy);
	}

	@Override
	public String getKey() {
		return getId();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	/*
	 * Builder class to create a new real world instance of a task
	 */
	public static class Builder {
		private String userTaskId;
		private String title;
		private String userMessage;
		private List<UserAction> actions = new ArrayList<UserAction>();
		
		private Builder(String title, String userMessage) {
			this.title = title;
			this.userTaskId = UUID.randomUUID().toString();
			this.userMessage = userMessage;
		}
	
		public Builder action(String label) throws ContextedException {
			URI uri;
			try {
				uri = new URI("usertasks/".concat(userTaskId));
			} catch (URISyntaxException e) {
				throw new ContextedException(e)
					.addContextValue("this",this);
			}
			actions.add(UserAction.create(userTaskId, label, uri));
			return this;
		}
	
		public UserTask build() {
			return new UserTask(userTaskId,new Date(),
					title,userMessage, actions,
					Status.OUTSTANDING,StringUtils.EMPTY,
					StringUtils.EMPTY,StringUtils.EMPTY,null,null);
		}
		
		@Override
		public String toString() {
			return ReflectionToStringBuilder.toString(this);
		}
	}
}
