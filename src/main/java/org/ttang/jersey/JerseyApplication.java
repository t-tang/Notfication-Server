package org.ttang.jersey;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.ttang.guice.GuiceInjector;

public class JerseyApplication extends ResourceConfig {
	@Inject
    public JerseyApplication(ServiceLocator serviceLocator) {
        
		packages(JerseyApplication.class.getPackage().getName());
		
		register(LoggingFilter.class);
		
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
    	GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
    	guiceBridge.bridgeGuiceInjector(new GuiceInjector());
    }
}