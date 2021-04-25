package com.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private UnshorterService unshorterService;

	@GetMapping("/start")
	public String start() {
		try {
			TelegramBot bot = new TelegramBot(getBotToken());
			bot.setUpdatesListener(updates -> {
				return onUpdateReceived(bot, updates);
			});
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return "Ok";
	}

	@GetMapping("/reset")
	public String reset() {
		GetGoogleSheetContent.resetBitlyTokensList();
		GetGoogleSheetContent.resetReplaceMap();
		ShortURLService.currentBitlyToken = null;
		return "Done";
	}

	public int onUpdateReceived(TelegramBot bot, List<Update> updates) {
		if (updates.size() > 12) {
			return UpdatesListener.CONFIRMED_UPDATES_ALL;
		}
		for (Update update : updates) {
			String changedDeal = null;
			if (update.message() != null && update.message().text() != null) {
				changedDeal = unshorterService.changedDeal(update.message().text());
				chatService.saveChat(update.message().chat().id());
			} else if (update.message() != null && update.message().caption() != null) {
				changedDeal = unshorterService.changedDeal(update.message().caption());
				chatService.saveChat(update.message().chat().id());
			}
			
			postMessage(bot, changedDeal);
		}
		return UpdatesListener.CONFIRMED_UPDATES_ALL;
	}

	private void postMessage(TelegramBot bot, String changedDeal) {
		if (changedDeal != null) {
			List<Chat> chats = chatService.getAllChats();
			for (Chat c : chats) {
				SendMessage snd = new SendMessage(c.getChatIdFromTelegram().toString(), changedDeal);
				snd.disableWebPagePreview(true);
				SendResponse response = bot.execute(snd);
			}
		}
	}

	public String getBotToken() {
		return Constants.BOTTOKEN;
	}

}
