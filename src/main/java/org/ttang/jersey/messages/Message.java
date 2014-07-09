package org.ttang.jersey.messages;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/*
 * Message class which enforces userMessage on error
 */
public class Message<T> {
	
	public enum Status {OK, ERR};
	
	private String messageType;
	private Status status = Status.ERR;
	private String userMessage;
	private T body;

	private Message(String messageType, Status status, String userMessage, T body) {

		this.messageType = messageType;
		this.status = status;
		this.userMessage = userMessage;
		this.body = body;
	}
	
	/* Allow empty userMessage if statusCode is OK */
	public static <T> Message<T> ok(T body) {
		MessageType messageType = body.getClass().getAnnotation(MessageType.class);
		return new Message<T>(messageType.name(),Status.OK,StringUtils.EMPTY,body);
	}
	
	/* Allow userMessage if statusCode is OK */
	public static <T> Message<T> ok(T body, String userMessage) {
		MessageType messageType = body.getClass().getAnnotation(MessageType.class);
		return new Message<T>(messageType.name(),Status.OK,userMessage,body);
	}
	
	/* Require userMessage is status code is err */
	public static <T> Message<T> err(T body,String userMessage) {
		MessageType messageType = body.getClass().getAnnotation(MessageType.class);
		return new Message<T>(messageType.name(),Status.OK,userMessage,body);
	}

	/* Empty body */
	public static <T> Message<T> ok(Class<?> type) {
		MessageType messageType = type.getAnnotation(MessageType.class);
		return new Message<T>(messageType.name(),Status.OK,StringUtils.EMPTY,null);
	}

	/* Empty body */
	public static <T> Message<T> ok(Class<?> type, String userMessage) {
		MessageType messageType = type.getAnnotation(MessageType.class);
		return new Message<T>(messageType.name(),Status.OK,userMessage,null);
	}
	
	
	
	public int getStatusCode() {
		return status.ordinal();
	}
	
	public String getUserMessage() {
		return userMessage;
	}
	
	public String getMessageType() {
		return messageType;
	}
	
	@JsonInclude(Include.NON_NULL)
	public T getBody() {
		return body;
	}
}
