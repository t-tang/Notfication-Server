package org.ttang;

import java.util.UUID;

public class ServerIdentity {

	private String uuid = UUID.randomUUID().toString();
	
	public String id() {
		return uuid;
	}
	
	public static final String COOKIE_NAME = "serverIdentity";
}
