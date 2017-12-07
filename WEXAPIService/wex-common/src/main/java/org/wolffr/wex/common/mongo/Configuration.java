package org.wolffr.wex.common.mongo;

import java.io.Serializable;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity("configuration")
public class Configuration implements Serializable {

	private static final long serialVersionUID = -5873247837220428719L;
	@Id
	private ObjectId id;
	@Property(value="mailUsername")
	private String mailUsername;
	@Property(value="mailPassword")
	private String mailPassword;
	@Property(value="mailReceiver")
	private String mailReceiver;
	@Property(value="boxVips")
	private String boxVip;

	public String getMailUsername() {
		return mailUsername;
	}

	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailReceiver() {
		return mailReceiver;
	}

	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}

	public String getBoxVip() {
		return boxVip;
	}

	public void setBoxVip(String boxVip) {
		this.boxVip = boxVip;
	}

}
