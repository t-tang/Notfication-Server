package org.ttang.jersey.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/*
 * Instruct the browser to allow cross origin REST 
 * Speeds up development and safe as long as authentication/entitlement is checked for each request
 */
@Provider
public class CrossOriginResponseFilter implements ContainerResponseFilter {

	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	
	public CrossOriginResponseFilter() {
		String thisClassName = this.getClass().getCanonicalName();
		Logger.getLogger(thisClassName).info("installed");
	}

	@Override
	public void filter(ContainerRequestContext request,
			ContainerResponseContext response) throws IOException {

        if (response.getHeaders().getFirst(ACCESS_CONTROL_ALLOW_ORIGIN) == null) {
        	response.getHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        	response.getHeaders().add(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
        }
	}
}
