package com.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {

	@Autowired
	public static UnshorterService unshorterService = new UnshorterService();

	@Autowired
	public static ChatService chatService;
}
