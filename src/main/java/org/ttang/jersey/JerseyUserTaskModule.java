package org.ttang.jersey;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.ttang.UserAction;
import org.ttang.UserTask;
import org.ttang.UserTaskStore;
import org.ttang.jersey.messages.Ember;
import org.ttang.jersey.messages.UserTaskActionResponse;

@Singleton
@Path("/usertasks")
public class JerseyUserTaskModule {
	@Inject 
	UserTaskStore userTaskStore;

	@Inject
	MessageBroadcaster messageBroadcaster;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.ok().entity(Ember.wrap(userTaskStore.getAll())).build();
	}

	@GET
	@Path("{taskId}/action/{actionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response action(
			@PathParam("taskId") String taskId,
			@PathParam("actionId") String actionId) {

		try {
			// Retrieve the User Task and action
			if (StringUtils.isEmpty(taskId) || StringUtils.isEmpty(actionId)) {
				return Response.status(Status.BAD_REQUEST).build();
			}

			UserTask userTask = userTaskStore.get(taskId);
			if (userTask == null) {
				return UserTaskActionResponse.taskNotFound(taskId,actionId);
			}
			
			// Reject action if the task already in progress
			// Update the calling client with the latest image
			if (!userTask.isOutstanding()) {
				return UserTaskActionResponse.userTask(userTask);
			}

			// Get the action from the task
			UserAction action = userTask.getAction(actionId);
			if (action == null) {
				return UserTaskActionResponse.actionNotFound(taskId, actionId);
			}
			
			// Mark the task as in progress
			userTask.action(actionId, "A.N. Other");
			UserTask actionedUserTask = userTaskStore.put(userTask);
			if (actionedUserTask == null) {
				// Race condition, the userTask was actioned by someone else
				// Update the client
				return UserTaskActionResponse.userTask(userTaskStore.get(userTask.getId()));
			}
			// Broadcast the actioned message
			messageBroadcaster.broadcast(Ember.wrap(actionedUserTask));

			// TODO: perform the action
			Thread.sleep(2000);

			// Mark the task as completed and update the store
			actionedUserTask.complete();
			UserTask completedTask = userTaskStore.put(actionedUserTask);
			if (completedTask == null) {
				// Race condition, should never happen happen
				return UserTaskActionResponse.userTask(userTaskStore.get(taskId));
			}

			// Broadcast the completed user task
			messageBroadcaster.broadcast(Ember.wrap(completedTask));

			// real response dispatched by broadcaster
			return Response.noContent().build();

		} catch (Throwable t ) {
			t.printStackTrace();
		}

		return UserTaskActionResponse.serverError(taskId, actionId);
	}
}
