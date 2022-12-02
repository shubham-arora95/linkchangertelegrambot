package com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	/*
	 * @Autowired private ChatRepository chatRespository;
	 */

	private String chatIds = "758505898,-1001481268881,835228747";

	public List<Chat> getAllChats() {
		List<Chat> chats = new ArrayList<>();

		String chatsToSendMessage = Constants.CHAT_IDS_TO_SEND_MESSAGE != null ? Constants.CHAT_IDS_TO_SEND_MESSAGE
				: chatIds;

		chats = Arrays.stream(chatsToSendMessage.split(",")).map(chat -> new Chat(Long.parseLong(chat.trim())))
				.collect(Collectors.toList());

		return chats;
	}

	/*
	 * public void saveChat(Long chatIdFromTelegram) { chatRespository.save(new
	 * Chat(chatIdFromTelegram)); }
	 */

}
