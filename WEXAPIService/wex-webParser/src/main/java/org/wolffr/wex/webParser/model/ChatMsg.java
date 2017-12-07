package org.wolffr.wex.webParser.model;

public class ChatMsg {
	private String username;
	private String msgId;
	private String message;
	private String time;

	public ChatMsg(String username, String msgId, String message, String time) {
		super();
		this.username = username;
		this.msgId = msgId;
		this.message = message;
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getMessage() {
		return message;
	}

	public String getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return msgId+"   "+ time + "   " +username + ": " + message;
	}

}
