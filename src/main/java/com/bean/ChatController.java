package com.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@RestController
public class ChatController extends TelegramLongPollingBot {

	@Autowired
	private ChatService chatService;

	@Autowired
	private UnshorterService unshorterService;

	@GetMapping("/start")
	public String start() {
		TelegramBotsApi telegramBotApi;
		try {
			telegramBotApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotApi.registerBot(this);
		} catch (TelegramApiException e1) {
			e1.printStackTrace();
		}

		return "Ok";
	}

	@GetMapping("/reset")
	public String reset() {
		GetGoogleSheetContent.resetBitlyTokensList();
		GetGoogleSheetContent.resetReplaceMap();
		UnshorterService.currentBitlyToken = null;
		return "Done";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.getMessage() != null) {
			String changedDeal = unshorterService.changedDeal(update.getMessage().getText());
			chatService.saveChat(update.getMessage().getChatId());
			List<Chat> chats = chatService.getAllChats();
			for (Chat c : chats) {
				SendMessage snd = new SendMessage();
				snd.disableWebPagePreview();
				snd.setChatId(c.getChatIdFromTelegram().toString());
				snd.setText(changedDeal);
				try {
					execute(snd);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}

		} else if (update.getChannelPost() != null) {
			chatService.saveChat(update.getChannelPost().getChat().getId());
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
