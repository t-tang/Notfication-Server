package org.ttang;

import javax.inject.Inject;

import org.apache.commons.lang3.exception.ContextedException;

public class UserTaskStoreInitializer {
	
	@Inject
	public UserTaskStoreInitializer(UserTaskStore userTaskStore) throws ContextedException {

		UserTask userTask = UserTask
			.builder("Cross","B 20,000 HSBA.L @ 12.5 with 10,000 @ market")
			.action("Cross")
			.action("Don't Cross")
			.build();

		userTaskStore.put(userTask);

		userTask = UserTask
			.builder("Barnum Foogly","B 20,000 HSBA.L @ 12.5")
			.action("Accept")
			.action("Decline")
			.build();

		userTaskStore.put(userTask);

	}
}
