package com.satya.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	@Autowired
	private JavaMailSender mailSender;

	public boolean sendMail(String subject, String body, String to) {
		boolean isSent = true;
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper msgHelper = new MimeMessageHelper(msg);
		try {
			msgHelper.setTo(to);
			msgHelper.setSubject(subject);
			msgHelper.setText(body, true);

			mailSender.send(msg);
		} catch (MessagingException e) {
			isSent = false;
			e.printStackTrace();
		}

		return isSent;
	}
}
