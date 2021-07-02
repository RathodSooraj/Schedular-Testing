package com.demo.hospital.managment.schedulerservice.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author suraj
 *
 */
public class EmailUtil {
	@Autowired
	private JavaMailSender sender;

	/*
	 * function to send the mail
	 */
	
	public boolean sendEmail(String to, String subject, String text, String[] cc, String[] bcc, MultipartFile file) {
		boolean flag = false;

		try {
//			creating empty message
			MimeMessage message = sender.createMimeMessage();
			// use helper class object
			MimeMessageHelper helper = new MimeMessageHelper(message, file != null);

			// setting up message details
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			if (cc != null)
				helper.setCc(cc);
			if (bcc != null)
				helper.setBcc(bcc);
			if (file != null) {
				helper.addAttachment(file.getOriginalFilename(), file);
			}
			// sending email
			sender.send(message);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/*
	 * function with the minimum requirment
	 */
	public boolean sendEmail(String to, String subject, String text) {
		return sendEmail(to, subject, text);
	}

}
