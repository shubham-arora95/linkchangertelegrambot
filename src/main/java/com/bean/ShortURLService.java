package com.bean;

import java.util.List;

import fr.plaisance.bitly.Bit;
import fr.plaisance.bitly.Bitly;

public class ShortURLService {
	public static String currentBitlyToken = null;
	public static int currentBitlyIndex = 0;
	public static List<String> bitlyTokens = GetGoogleSheetContent.getBitlyTokensList();

	public static String shortURL(String unshortedURL) {
		if (currentBitlyToken == null) {
			currentBitlyToken = bitlyTokens.get(currentBitlyIndex);
		}
		String shortURL = null;
		if (unshortedURL != null && unshortedURL.length() > 0) {
			for(String token : bitlyTokens) {
				try {
					Bitly bitly = Bit.ly(token);
					shortURL = bitly.shorten(unshortedURL);
					if(shortURL != null) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return shortURL;
	}
}
