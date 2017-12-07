package org.wolffr.wex.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PublicApi {

	private static final String TICKERURI = "https://wex.nz/api/3/ticker/";
	private static final String DEPTHURI = "https://wex.nz/api/3/depth/";

	public static String getRecentTicker(String symbol) throws Exception {
		return fetchData(TICKERURI, symbol);
	}

	public static String getCurrentDepth(String symbol) throws Exception {
		return fetchData(DEPTHURI, symbol);
	}

	private static String fetchData(String uri, String symbol) throws Exception {
		String url = uri + symbol;
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}
