package com.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetGoogleSheetContent {

	private static String replaceSheet = "https://docs.google.com/spreadsheets/u/1/d/e/2PACX-1vQPaC0tVCws9zCHhNKMAKECZ3Qm2ItgNp6j1Z3y3ODN974lzRNPZPq36WdhIvjVqcAfmg0jS0dr5Ni2/pubhtml";

	private static String bitlyTokenSheet = "https://docs.google.com/spreadsheets/u/1/d/e/2PACX-1vRDhPC9KIT4rN--YZ2tHEsjk0JZiQtfllmAJQrJXEg4N2p6v0YnlzRKDxn9pAjoENCpuvQS6GzUEt02/pubhtml";

	private static Map<String, String> replaceMap = null;
	private static List<String> bitlyTokens = null;

	public static Map<String, String> getReplaceSheetMap() {
		if (replaceMap == null) {
			replaceMap = getReplaceSheetContent();
		}
		return replaceMap;
	}

	public static void resetReplaceMap() {
		replaceMap = null;
	}

	public static List<String> getBitlyTokensList() {
		if (bitlyTokens == null) {
			bitlyTokens = getAllBitlyTokensContent();
		}
		return bitlyTokens;
	}

	public static void resetBitlyTokensList() {
		bitlyTokens = null;
	}

	private static Map<String, String> getReplaceSheetContent() {
		Map<String, String> returnMap = new HashMap<>();
		try {
			Document doc = Jsoup.connect(replaceSheet).get();
			Elements elements = doc.getElementsByTag("tr");
			for (int i = 1; i < elements.size(); i++) {
				Element e = elements.get(i);
				String value = e.getElementsByTag("td").get(0).text();
				String replace = e.getElementsByTag("td").get(1).text();
				returnMap.put(value, replace);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMap;
	}

	private static List<String> getAllBitlyTokensContent() {
		List<String> returnList = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(bitlyTokenSheet).get();
			Elements elements = doc.getElementsByTag("tr");
			for (int i = 1; i < elements.size(); i++) {
				Element e = elements.get(i);
				String value = e.getElementsByTag("td").get(0).text();
				returnList.add(value);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	public static String getMRPFromAmazon(String amazonURL) {
		try {
			Integer mrp = null;
			Document doc = Jsoup.connect(amazonURL).userAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.38 Safari/537.36")
					.get();
			Elements elements = doc.getElementsByClass("priceBlockStrikePriceString");
			if (elements.size() > 0) {
				String mrpString = elements.get(0).text().replaceAll("₹", "");
				mrpString = mrpString.replaceAll(" ", "");
				mrpString = mrpString.replaceAll(",", "");
				Double mrpDouble = Double.parseDouble(mrpString);
				mrp = mrpDouble.intValue();
			}
			return mrp.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getMRPFromFlipkart(String flipkartURL) {
		try {
			Integer mrp = null;
			Document doc = Jsoup.connect(flipkartURL).userAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.38 Safari/537.36")
					.get();
			Elements elements = doc.getElementsByClass("_3I9_wc _2p6lqe");
			if (elements.size() > 0) {
				String mrpString = elements.get(0).text().replaceAll("₹", "");
				mrpString = mrpString.replaceAll(" ", "");
				mrpString = mrpString.replaceAll(",", "");
				Double mrpDouble = Double.parseDouble(mrpString);
				mrp = mrpDouble.intValue();
			}
			return mrp.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
