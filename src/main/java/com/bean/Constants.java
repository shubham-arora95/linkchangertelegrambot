package com.bean;

public class Constants {
	public static final String USERNAME = System.getenv("BOT_USERNAME");
	public static final String BOTTOKEN = System.getenv("BOT_TOKEN");
	public static final String THREADPOOL_SIZE =  System.getenv("THREADPOOL_SIZE");
	public static final String CHAT_IDS_TO_SEND_MESSAGE = System.getenv("CHAT_IDS");
}
