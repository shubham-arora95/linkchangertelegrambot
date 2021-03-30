package com.bean;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class FirstBot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		String changedDeal = ServiceFactory.unshorterService.changedDeal(update.getMessage().getText());
		ServiceFactory.chatService.getAllChats();
		SendMessage snd = new SendMessage();
		snd.setChatId(update.getMessage().getChatId().toString());
		snd.setText(changedDeal);

		try {
			execute(snd);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return Constants.USERNAME;
	}

	@Override
	public String getBotToken() {
		return Constants.BOTTOKEN;
	}

}
