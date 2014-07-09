package org.ttang.jersey;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ttang.ServerIdentity;
import org.ttang.jersey.messages.LoginResponse;

@Path("/login")
public class JerseyLoginModule {
	@Inject
	private ServerIdentity serverIdentity;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response login() {
		// No real authentication/entitlement for now
		return LoginResponse.create(serverIdentity.id());
	}
}