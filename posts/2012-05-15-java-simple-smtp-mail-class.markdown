---
title: Java - Simple Smtp Mail Class
author: Kenny Cason
tags: java, mail, smtp
---

Just a few quick classes I whipped up for bare minimum SmtpMail support.
also don't rely on the default javax.mail package. I believe the Java SDK only provides the APIs and not the implementations. 
I ended up downloading the implementation from GlassFish: <a href="http://download.java.net/maven/glassfish/org/glassfish/extras/glassfish-embedded-all/3.1.1/" target="_blank">glassfish-embedded-all-3.1.1.jar</a>

SmtpMail.java

```{.java .numberLines startFrom="1"}
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpMail {

	private final MailConfig config;

	public SmtpMail(MailConfig config) {
		this.config = config;
	}
	
	public boolean send(String to, String from, String subject, String text) {
		return send(new String[] {to}, from, subject, text);
	}

	public boolean send(String[] to, String from, String subject, String text) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", config.host);
		props.put("mail.smtp.user", config.username);
		props.put("mail.smtp.port", config.port);
		props.put("mail.smtp.password", config.password);
		
		Session session = Session.getInstance(props, new SmtpAuthenticator(config));

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}
			message.setRecipients(Message.RecipientType.TO, addressTo);
			message.setSubject(subject);
			message.setText(text);

		   Transport.send(message);

		} catch (MessagingException e) { 
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

```

SmtpAuthenticator.java

```{.java .numberLines startFrom="1"}
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {

	private MailConfig config;
	
	public SmtpAuthenticator(MailConfig config) {
		super();
		this.config = config;
	}
		
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
	    if ((config.username != null) && (config.username.length() > 0) &&
	    		(config.password != null) && (config.password.length   () > 0)) {
	        return new PasswordAuthentication(config.username, config.password);
	    }
	    return null;
	}

}

```

MailConfig.java

```{.java .numberLines startFrom="1"}
public class MailConfig {
	
	public String host = "";
	
	public int port = 587;
	
	public String username = "";
	
	public String password = "";

}

```

MailConfigFactory.java

```{.java .numberLines startFrom="1"}
public class MailConfigFactory {
	
	private MailConfigFactory() {
		
	}
	
	public static MailConfig buildDefaultConfig() {
		MailConfig config = new MailConfig();
		config.host = "smtp.host.com";
		config.port = 1234;
		config.username = "username";
		config.password = "password";
		
		return config;
	}

}

```

SmtpMailTest.java

```{.java .numberLines startFrom="1"}
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SMTPMailTest {

	@Test
	public void test() {
		SmtpMail mail = new SmtpMail(MailConfigFactory.buildDefaultConfig());
		String to = "to@gmail.com";
		String from = "from@gmail.com";
		String subject = "Test Subject";
		String text = "This is a sample message body";
		
		assertTrue(mail.send(to, from, subject, text));
	}

}

```
