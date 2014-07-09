package org.ttang.jersey.messages;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

public class LoginResponse {
	
	public static LoginResponse loginResponse = new LoginResponse();

	public static Response create(String serverIdentity) {
		return Response.ok(Ember.wrap(loginResponse))
				.cookie(new NewCookie("serverIdentity",serverIdentity))
				.build();
	}
}
