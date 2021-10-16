package com.bean;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateFlipkartShortLinks {
	private final OkHttpClient httpClient = new OkHttpClient();

	private static final String COOKIE = System.getenv("FLIPKART_COOKIE") != null ? System.getenv("FLIPKART_COOKIE")
			: "_ga=GA1.2.1443384648.1614274179; _gid=GA1.2.2095112129.1614274179; _gat=1; JSESSIONID=1txe6t4kh5b8x1w97p8ovi4tek71767; T=TI161427419332600137507141941674413018854790843406843833414832306201; SN=VI587C0D83B87B4E24959C84ADAC004C45.TOK2D8E8444927343B1AE02439B5DBD7976.1614274193.LO; gpv_pn=HomePage; gpv_pn_t=FLIPKART:HomePage; AMCVS_17EB401053DAF4840A490D4C@AdobeOrg=1; s_cc=true; AMCV_17EB401053DAF4840A490D4C@AdobeOrg=-227196251|MCIDTS|18684|MCMID|13871163786038870340296640353493149829|MCAAMLH-1614878995|12|MCAAMB-1614878995|6G1ynYcLPuiQxYZrsz_pkqfLG9yMXBpb2zX5dvJdYQJzPXImdj0y|MCOPTOUT-1614281396s|NONE|MCAID|NONE; S=d1t10Dj8/Pz8/ew4DPzdbPxk/ECapJZ5HoF61MXoqmrs0rm+h2EaH36Pd9JuI1jhwaXNpYO3iNKU2yw+5DtM+/IE0ng==";

	private static final String AMAZON_COOKIE = System.getenv("AMAZON_COOKIE") != null ? System.getenv("AMAZON_COOKIE")
			: "session-id=262-6764007-9044148; i18n-prefs=INR; ubid-acbin=262-9814229-7271156; session-token=\"c+pteYqOqy6Jlnubpw2expbpWjz6eELhArNQ0Xp5jNUXRM0/DIFBcJf33N576L5upj5sDkJNJVeiw7GiTIZVKB3tK3KO0c4ZNh2gilZ1IfKYEqpmHsP4BORaMmQTw4I4muIv2UjEnb8FknugQPx/I6kssws71BVujqUHOAUNFKi5dJbCsa+0apgHbs3lJ5aENnwsL8RQFcRutCBdV0KDOcMu9K1iV/R+gEWpVeI6CTaLpANQkx3kuaNS1dBeIzHgqRJup3AluisHMeIRrSrAHA==\"; x-acbin=\"WmgygDk16?fx6zMUTxJRSemFrGeJRZn@A9dasOD7XrbixZIyIOKPuPQZlSfjZIdM\"; at-acbin=Atza|IwEBIIvAXmUcRhRDs6MtUW7UHd7-fz85nXmsEyk0Zz9mDmh66cADOa4URdBn9DkPZ3GF9dujVJG-C7rr1Pf11gEcluE1dbHjMxiZMYDKm6v1ujw7tz0UPQLUTp4ym9K3asWYfDhveShozFZ3WF4CDzSThztunRS1NoDPOMSTE3ug6UQ3HVG1DyJKE6PQvmh8xVxuDKd35Nr05B5QvPBfYQ7tMJGGImfZHWPvIlWE8W3J_KMaHwbG1Vu9lVJ0-cPPYSFO71g; sess-at-acbin=\"A34ZbCwP+b0mns1h7GBb54uHtXX85bj3aoapavRkE30=\"; sst-acbin=Sst1|PQFNXA-6nYfWfWVZ0X9UV7QxCey3kTMTpI9WzmUO-lv5tWdJphuVHP5edNixMruhJq0qgOq7wkg6CkD-BA1hMJtUnxvlmyiZquhwLcJj-22WTWGuoGsr1HklHThc1W3FxY22UE_KJ9wGX0xwIEzpgBTIPUID-S-kmlzeXn7oSLKXkH1Oj0tlcHd_uagpz5CquViTZL7mt7QWy--4syLZ7YB-N2lsuNEITIDrIio65yJTtJWcHmZxfvzGmgWcTIOfU_-34lK2hhcEN92IB6QGwo-FPIZrNYR7y1U0AMUtwxW-Q4k; _rails-root_session=VG5xVlA0NTY2T3RXankzWUhpWm5JWmNhNjlaS2lqWnp1QzZFUTdIS2ZWa1dOS1p4MFd0eTdUbExxbCtaVlA3bVVyWjBSTnRkZU1rQTlpRVdjdGcxcmpINjYzczdKeC9nTURIcmJqbHg0cHR0RnlEazE3eUFVVUs0QUFUTGgybEVxZmhDKzlkT1puZ1ZzWFhHVHJKajE2SW1LYVR3UTNoWWkrL1VSaXhVUXFpc3RVWXFZd2hNM1ZpRTFEcDdOOERjLS1EVW1MVzRaQW1mU1dlLzBGZWV3R2hnPT0=--e819915158937e23b5f35510434211b90a2d14e6; s_fid=17A28B5EE96A57FE-2A77D70C5D290F79; s_cc=true; visitCount=2; csm-hit=tb:N8D7ZV9CQWVC6JB604JT+s-N8D7ZV9CQWVC6JB604JT|1614332834071&t:1614332834072&adb:adblk_yes; session-id-time=2082787201l";

	private static boolean generateFKThroughEarnly = System.getenv("EARNLY") != null
			? Boolean.parseBoolean(System.getenv("EARNLY"))
			: false;

	private static String earnlyAuthorization = null;

	private static final String EARNLY_USERNAME = "nonstopdeals.in@gmail.com";

	private static final String EARNLY_PASSWORD = "!Mayaramkishiv1";

	public String generateFlipkartShortLinks(String fullUnchangedURL) {
		String changedURL = null;
		if (generateFKThroughEarnly) {
			changedURL = generateEarnlyFlipkartLinks(getFullFlipkartURL(fullUnchangedURL));
			if (changedURL == null) {
				changedURL = generateShortURL(getFullFlipkartURL(fullUnchangedURL));
			}
		} else {
			changedURL = generateShortURL(getFullFlipkartURL(fullUnchangedURL));
		}
		return changedURL;
	}

	private String loginOnEarnly() {
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("isGluv", true);
		jsonObject.addProperty("userName", EARNLY_USERNAME);
		jsonObject.addProperty("password", EARNLY_PASSWORD);
		jsonObject.addProperty("source", 2);

		RequestBody body = RequestBody.create(JSON, jsonObject.toString());

		URL url = new HttpUrl.Builder().scheme("https").host("red.gopaisa.com").addPathSegment("auth")
				.addPathSegment("login").build().url();

		Request request = new Request.Builder().url(url).post(body).build();

		try (Response response = httpClient.newCall(request).execute()) {

			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);

			String autorization = response.header("authorization").split(":")[0];

			return autorization;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private String generateEarnlyFlipkartLinks(String fullFlipkartURL) {
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("url", fullFlipkartURL);

		RequestBody body = RequestBody.create(JSON, jsonObject.toString());

		URL url = new HttpUrl.Builder().scheme("https").host("red.gopaisa.com").addPathSegment("gotostore")
				.addPathSegment("get-affiliate-link").build().url();

		if (earnlyAuthorization == null) {
			earnlyAuthorization = loginOnEarnly();
			if (earnlyAuthorization == null) {
				return null;
			}
		}

		Request request = new Request.Builder().url(url).addHeader("authorization", earnlyAuthorization).post(body)
				.build();

		try (Response response = httpClient.newCall(request).execute()) {

			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);

			String jsonString = response.body().string();
			HashMap<String, String> responseMap = new Gson().fromJson(jsonString,
					new TypeToken<HashMap<String, String>>() {
					}.getType());

			return responseMap.get("link");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String getFullFlipkartURL(String unshortFlipkartURL) {

		URL url = new HttpUrl.Builder().scheme("https").host("affiliate.flipkart.com").addPathSegment("a_url_gen")
				.addQueryParameter("url", unshortFlipkartURL).build().url();
		Request request = new Request.Builder().url(url).addHeader("Cookie", COOKIE).build();

		try (Response response = httpClient.newCall(request).execute()) {

			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);

			String jsonString = response.body().string();
			HashMap<String, Object> map = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			LinkedTreeMap<String, String> responseMap = (LinkedTreeMap<String, String>) map.get("response");
			return responseMap.get("converted_url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public String generateShortURL(String unshortURL) {

		URL url = new HttpUrl.Builder().scheme("https").host("affiliate.flipkart.com").addPathSegment("a_url_shorten")
				.addQueryParameter("url", unshortURL).build().url();
		Request request = new Request.Builder().url(url).addHeader("Cookie", COOKIE).build();

		try (Response response = httpClient.newCall(request).execute()) {

			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);

			String jsonString = response.body().string();
			HashMap<String, Object> map = new Gson().fromJson(jsonString.toString(), HashMap.class);
			LinkedTreeMap<String, String> responseMap = (LinkedTreeMap<String, String>) map.get("response");
			return responseMap.get("shortened_url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public String generateAmazonShortLinks(String longLink) {

		URL url = new HttpUrl.Builder().scheme("https").host("amazon.in").addPathSegment("associates")
				.addPathSegment("sitestripe").addPathSegment("getShortUrl")
				.addEncodedQueryParameter("longUrl", longLink).addQueryParameter("marketplaceId", "44571").build()
				.url();
		Request request = new Request.Builder().url(url).addHeader("Cookie", AMAZON_COOKIE).build();

		try (Response response = httpClient.newCall(request).execute()) {

			if (!response.isSuccessful())
				return ShortURLService.shortURL(longLink);

			String jsonString = response.body().string();
			HashMap<String, Object> map = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (map.get("shortUrl") != null)
				return (String) map.get("shortUrl");
			else if (map.get("shortUrl") == null && map.get("longUrl") != null)
				return ShortURLService.shortURL(longLink);
			return longLink;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
