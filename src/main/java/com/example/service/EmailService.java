package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;

@Controller
@RequestMapping("")
public class EmailService {

	@Autowired
	private MailSender sender;
	
	@RequestMapping("/send-mail")
	public String sendMail(Order order) {
		String email = order.getDestinationEmail();  
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("件名");
		msg.setText("本文");
		sender.send(msg);
		return "finish";
//		try {
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}	
}
