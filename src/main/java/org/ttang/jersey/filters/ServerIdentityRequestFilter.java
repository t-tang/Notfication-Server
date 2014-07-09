package org.ttang.jersey.filters;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.ttang.ServerIdentity;

//@Provider
public class ServerIdentityRequestFilter implements ContainerRequestFilter {

	private static final URI uriLogin = URI.create("/");
	private static final String loginPath = "login";
	@Inject private ServerIdentity serverIdentity;

	public ServerIdentityRequestFilter() {
		String thisClassName = this.getClass().getCanonicalName();
		Logger.getLogger(thisClassName).info("installed");
	}
	
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		// Skip server identity check for login
		if (context.getUriInfo().getPath().equals(loginPath)) {
			return;
		}

		if (context.getCookies().containsKey(ServerIdentity.COOKIE_NAME)) {
			if (context.getCookies().get(ServerIdentity.COOKIE_NAME).getValue().equals(serverIdentity.id())) {
				// Passed the server identity check
				return;
			}
		}

		// Redirect to login page if the client has not previously logged into this server
		// Should really redirect to page with error information
		context.abortWith(
				Response.seeOther(uriLogin).build());
	}
}
