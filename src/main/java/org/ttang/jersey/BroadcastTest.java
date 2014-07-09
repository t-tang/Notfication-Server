package org.ttang.jersey;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.ttang.jersey.messages.SubscribeResponse;

@Path("/test")
public class BroadcastTest {
	@Inject
	MessageBroadcaster messageBroadcaster;

	@GET
	public Response test() {
		messageBroadcaster.broadcast(SubscribeResponse.ok());
		return Response.ok().build();
	}
}
