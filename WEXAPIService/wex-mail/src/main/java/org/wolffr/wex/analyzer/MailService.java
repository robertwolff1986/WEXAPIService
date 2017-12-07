package org.wolffr.wex.analyzer;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.wolffr.wex.mongo.ConfigurationStore;

@Singleton
@Startup
public class MailService {
	
	@EJB
	private ConfigurationStore configurationStore;

	public void sendPercentSwingMail(String symbol, Double percentSwing, String old, String current) {
		sendMail("Swing: " + symbol, symbol + ": " + percentSwing + "% " + System.lineSeparator() + "oldValue:" + old
				+ " -> newValue:" + current);
	}
	
	public void sendTrollBoxMsg(String message, String username) {
		sendMail("TrollBoxInfo: " + username, message);
	}

	private void sendMail(String subject, String messageToSend) {

		Session session = createSession();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("WEXAPIService@rw.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(configurationStore.getConfiguration().getMailReceiver()));
			message.setSubject(subject);
			message.setText(messageToSend);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private Session createSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(configurationStore.getConfiguration().getMailUsername(), configurationStore.getConfiguration().getMailPassword());
			}
		});
		return session;
	}

}
