package org.ttang.jersey.messages;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ttang.UserTask;

public class Ember {
	private static Map<String,Object> wrap(Object object, String name) {
		HashMap<String, Object> map = new HashMap<String,Object>(1);
		map.put(name, object);
		return map;
	}
	
	public static Map<String,Object> wrap (Collection<UserTask> userTasks) {
		return wrap(userTasks,"usertasks");
	}
	
	public static Map<String,Object> wrap (UserTask userTask) {
		return wrap(userTask,"usertask");
	}
	
	public static Map<String,Object> wrap (UserTaskActionResponse response) {
		return wrap(response,"userTaskActionResponse");
	}
	
	public static Map<String,Object> wrap (LoginResponse response) {
		return wrap(response,"loginResponse");
	}
	
}
