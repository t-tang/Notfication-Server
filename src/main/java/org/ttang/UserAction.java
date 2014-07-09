package org.ttang;

import java.net.URI;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.rits.cloning.Immutable;

@Immutable
public class UserAction {

	private String id;
	/* Label for this action, e.g. button text */
	private String label;
	/* Rest url to execute this action */
	private String url;
	
	/* Create a new real world instance of a UserAction */
	public static UserAction create(String userTaskId, String label, URI uri) {
		// Assign an id for the action
		String id = UUID.randomUUID().toString();

		// Append the action fragment to the uri
		String url = uri.normalize().toString();
		url = url.concat(url.endsWith("/") ? StringUtils.EMPTY : "/").concat("action/").concat(id);

		return new UserAction(id,userTaskId,label, url);
	}

	/* Create an in memory representation of a UserAction */
	private UserAction(String id, String userTaskId, String label, String url) {
		this.id = id;
		this.label = label;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getUrl() {
		return url;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
