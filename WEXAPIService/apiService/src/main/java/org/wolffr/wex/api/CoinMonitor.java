package org.wolffr.wex.api;

import java.io.IOException;

import org.bson.json.JsonParseException;
import org.json.JSONObject;
import org.wolffr.wex.common.mongo.ticker.SingleTicker;
import org.wolffr.wex.mongo.TickerStore;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinMonitor {

	private TickerStore tickerStore;
	private String symbol;

	public CoinMonitor(String symbol, TickerStore tickerStore) {
		System.out.println("Created CoinMonitor with symbol: " + symbol);
		this.symbol = symbol;
		this.tickerStore = tickerStore;
	}

	public void monitor() throws InterruptedException {
		try {
			consumeTicker(getRecentTicker());
			Thread.sleep(2000);
			monitor();
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(250);
			monitor();
		}
	}

	private void consumeTicker(String lastTickerJSON) {
		try {
			SingleTicker singleTicker = createTickerFromJSON(lastTickerJSON);
			tickerStore.process(singleTicker);
		} catch (JsonParseException | IOException e) {
			System.out.println("Could not consume ticker: " + e.getStackTrace());
		}
	}

	private String getRecentTicker() {
		try {
			return PublicApi.getRecentTicker(symbol);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private SingleTicker createTickerFromJSON(String lastTickerJSON)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		SingleTicker ticker = mapper.readValue(new JSONObject(lastTickerJSON).getJSONObject(symbol).toString(),
				SingleTicker.class);
		ticker.setSymbol(symbol);
		return ticker;
	}
}
