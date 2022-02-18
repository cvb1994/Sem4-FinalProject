package com.personal.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.personal.common.EmailTypeEnum;
import com.personal.event.SendMailRegisterEvent;
import com.personal.serviceImp.MailService;

@Component
public class SendMailListener {
	@Autowired
	MailService mailService;

	@EventListener
    public void handleContextStart(SendMailRegisterEvent event) {
        System.out.println("Handling context started event." + event.getMessage());
        String type = event.getType();
        if(EmailTypeEnum.WELCOME.name.equals(type)) {
        	mailService.sendWelcomeMail(event.getMessage(), event.getReceiver());
        } else if(EmailTypeEnum.RESET.name.equals(type)) {
        	mailService.sendResetMail(event.getMessage(), event.getReceiver());
        }
        
    }
}
