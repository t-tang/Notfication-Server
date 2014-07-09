package org.ttang.jersey;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

// Wrapper for SseBroadcaster which takes a message object and sends JSON messages to the receiver
@Singleton
public class MessageBroadcaster {

	private SseBroadcaster sseBroadcaster = new SseBroadcaster();

	public boolean add(EventOutput eventOutput) {
		return sseBroadcaster.add(eventOutput);
	}

	// Convert to Message, and enforce JSON serialization
	public void broadcast(Object message) {
		OutboundEvent outboundEvent = new OutboundEvent.Builder()
			.data(message)
			.mediaType(MediaType.APPLICATION_JSON_TYPE)
			.build();

		sseBroadcaster.broadcast(outboundEvent);
	}
}