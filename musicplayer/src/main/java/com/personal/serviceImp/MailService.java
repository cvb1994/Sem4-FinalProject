package com.personal.serviceImp;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.personal.service.IMailService;

@Service
public class MailService implements IMailService {
	
	private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
	
	@Value("smtp.gmail.com")
    private String host;
    @Value("587")
    private String port;
    @Value("aptech.sem4.group2@gmail.com")
    private String email;
    @Value("mqdimgogkgsgatlc")
    private String password;
    
    @Autowired
    ThymeleafService thymeleafService;

	@Override
	public void sendWelcomeMail(String userName, String receiver) {
		System.out.println("da vao day 2");
		Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(receiver)});

            message.setFrom(new InternetAddress(email));
            message.setSubject("Welcome To Your Music World");
            message.setContent(thymeleafService.getWelcomeMail(userName), CONTENT_TYPE_TEXT_HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

	}

	@Override
	public void sendResetMail(String link, String receiver) {
		System.out.println("da vao day 2");
		Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(receiver)});

            message.setFrom(new InternetAddress(email));
            message.setSubject("Reset Your Password");
            message.setContent(thymeleafService.getResetEmail(link), CONTENT_TYPE_TEXT_HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}


}
