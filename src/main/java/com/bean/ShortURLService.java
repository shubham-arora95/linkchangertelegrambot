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
		int index = 0;
		Bitly bitly = Bit.ly(currentBitlyToken);
		String shortURL = null;
		if (unshortedURL != null && unshortedURL.length() > 0) {
			try {
				shortURL = bitly.shorten(unshortedURL);
			} catch (Exception e) {
				e.printStackTrace();
				if(currentBitlyIndex < bitlyTokens.size()) {
					currentBitlyIndex++;
					currentBitlyToken = bitlyTokens.get(currentBitlyIndex);
					shortURL(unshortedURL);
				}
			}
		}
		return shortURL;
	}
}
