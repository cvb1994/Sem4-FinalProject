package com.personal.service;

public interface IMailService {
	public void sendWelcomeMail(String userName, String receiver);
	public void sendResetMail(String link, String receiver);
}
