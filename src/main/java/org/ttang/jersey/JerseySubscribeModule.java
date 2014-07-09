package org.ttang.jersey;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

@Path("/subscribe")
public class JerseySubscribeModule {
	@Inject
	MessageBroadcaster messageBroadcaster;
	
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput subscribe() {
		EventOutput eventOutput = new EventOutput();
		messageBroadcaster.add(eventOutput);
		return eventOutput;
	}
}
