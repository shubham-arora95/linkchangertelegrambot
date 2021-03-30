package com.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	@Autowired
	private ChatRepository chatRespository;

	public List<Chat> getAllChats() {
		List<Chat> chats = new ArrayList<>();
		chatRespository.findAll().forEach(chats::add);
		return chats;
	}

	public void saveChat(Long chatIdFromTelegram) {
		chatRespository.save(new Chat(chatIdFromTelegram));
	}

}
