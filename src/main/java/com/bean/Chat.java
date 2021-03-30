package com.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Chat {

	@Id
	private Long chatIdFromTelegram;

	public Chat() {
	}

	public Chat(Long chatIdFromTelegram) {
		this.chatIdFromTelegram = chatIdFromTelegram;
	}

	public Long getChatIdFromTelegram() {
		return chatIdFromTelegram;
	}

	public void setChatIdFromTelegram(Long chatIdFromTelegram) {
		this.chatIdFromTelegram = chatIdFromTelegram;
	}

}
