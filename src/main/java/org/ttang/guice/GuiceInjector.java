package org.ttang.guice;

import java.util.Collections;

import org.ttang.UserTaskStoreInitializer;
import org.ttang.hk2.GuiceBridgeJitInjector;

import com.google.inject.Guice;
import com.google.inject.servlet.ServletModule;

public class GuiceInjector extends GuiceBridgeJitInjector {

	public GuiceInjector() {
		super(Guice.createInjector(new GuiceModule()),Collections.<String>singleton("org.ttang"));
	}

	private static class GuiceModule extends ServletModule {

		@Override
		protected void configureServlets() {
			// No need to bind Jersey modules, since Jersey will discover these packages according to it's package config

			// Application initialization
			bind(UserTaskStoreInitializer.class).asEagerSingleton();
		}
	}
}
