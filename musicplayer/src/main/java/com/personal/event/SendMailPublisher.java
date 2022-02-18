package com.personal.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SendMailPublisher {
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void sendMail(final String message, final String receiver, final String type) {
        System.out.println("Publishing custom event. ");
        SendMailRegisterEvent mailConfirmRegister = new SendMailRegisterEvent(this, message, receiver, type);
        applicationEventPublisher.publishEvent(mailConfirmRegister);
    }
}
