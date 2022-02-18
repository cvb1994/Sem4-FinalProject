package com.personal.event;

import org.springframework.context.ApplicationEvent;

public class SendMailRegisterEvent extends ApplicationEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String receiver;
	private String type;
	

    public SendMailRegisterEvent(Object source, String message, String receiver, String type) {
        super(source);
        this.message = message;
        this.receiver = receiver;
        this.type = type;
    }
    public String getMessage() {
        return message;
    }
    
    public String getReceiver() {
		return receiver;
	}
    
	public String getType() {
		return type;
	}
    
}
