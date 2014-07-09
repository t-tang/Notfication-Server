package org.ttang;

import java.net.URISyntaxException;

import javax.inject.Inject;

public class UserTaskStoreInitializer {
	
	@Inject
	public UserTaskStoreInitializer(UserTaskStore userTaskStore) throws URISyntaxException {

		UserTask userTask = UserTask
			.builder("Barnum Foogly","B 20,000 HSBA.L @ 12.5")
			.action("Accept")
			.action("Decline")
			.build();

		userTaskStore.put(userTask);
	}
}
