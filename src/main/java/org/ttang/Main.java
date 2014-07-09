package org.ttang; 
import java.util.logging.SimpleFormatter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.ttang.jersey.JerseyApplication;


public class Main {
	public static void main(String[] args) throws Exception {
		
		// Normalize the loggging output, no milliseconds available
		System.getProperties().setProperty(SimpleFormatter.class.getCanonicalName()+".format",
				"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");

		Server server = new Server(8080);
		ServletContextHandler sch = new ServletContextHandler(server, "/");

		// static content
		sch.addServlet(DefaultServlet.class,"/").setInitParameter("resourceBase", "webapps");
		
        ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
        jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyApplication.class.getCanonicalName());
        sch.addServlet(jerseyServletHolder, "/services/*");
		
		server.start();
		server.join();
	}
}
