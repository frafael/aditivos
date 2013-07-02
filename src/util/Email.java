package util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.Usuario;

public class Email {
	
	private String smtpHostName;
	private String smtpPort;
	private String smtpUser;
	
	public Email(Usuario usuario){
		this.smtpHostName = "";
		this.smtpPort = "";
		this.smtpUser = "";
	}

	public void send(String recipients[], String subject, String message) throws MessagingException {
		
		Properties props = new Properties();
			props.put("mail.smtp.host", smtpHostName);
			props.put("mail.smtp.port", smtpPort);
			props.put("mail.smtp.auth", "false");
			props.put("mail.smtp.starttls.enable", "false");
			
		Session session = Session.getDefaultInstance(props);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(smtpUser));
		msg.setSubject(subject);
		msg.setContent(message, "text/html; charset=UTF-8");
		
		msg.setHeader("Reply-To", "<nao-responda@grupofortes.com.br>");

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}

        msg.setRecipients(RecipientType.TO, addressTo);
        Transport.send(msg);
	}
}