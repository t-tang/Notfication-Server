package org.ttang.jersey.messages;

public class SubscribeResponse {
	
	private static final SubscribeResponse instance = new SubscribeResponse();
	
	private SubscribeResponse() {
	}
	
	public static Message<SubscribeResponse> ok() {
		return Message.<SubscribeResponse>ok(instance);
	}
}