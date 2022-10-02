package com.bean;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class DealTask extends Thread {
	
	private ChatService chatService;

	private UnshorterService unshorterService;

	private TelegramBot bot;

	private String message;



	public DealTask(ChatService chatService, UnshorterService unshorterService, TelegramBot bot, String message) {
		super();
		this.chatService = chatService;
		this.unshorterService = unshorterService;
		this.bot = bot;
		this.message = message;
	}

	@Override
	public void run() {
		String deal = unshorterService.changedDeal(message);
		postMessage(deal);
	}
	
	private void postMessage(String changedDeal) {
		if (changedDeal != null) {
			List<Chat> chats = chatService.getAllChats();
			for (Chat c : chats) {
				SendMessage snd = new SendMessage(c.getChatIdFromTelegram().toString(), changedDeal);
				snd.disableWebPagePreview(true);
				bot.execute(snd);
			}
		}
	}

}
