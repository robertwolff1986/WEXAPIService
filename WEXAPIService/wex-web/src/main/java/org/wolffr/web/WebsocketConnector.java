package org.wolffr.web;

import java.util.Arrays;

import org.json.JSONObject;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wolffr.wex.common.Symbol;
import org.wolffr.wex.common.mongo.ticker.SpecificTicker;

public class WebsocketConnector {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketConnector.class);
	
	
	public static void sendNotification(SpecificTicker ticker,SpecificTicker lastTicker) {
		LOGGER.info("Send notification for: " + ticker.getSymbol());
		EventBus eventBus = null;
		try {
			eventBus = EventBusFactory.getDefault().eventBus();
			String channel = getChannel(ticker);
			eventBus.publish(channel, getMessage(ticker,lastTicker));
		} catch (Exception e) {
			LOGGER.warn("Eventbus not yet ready");
		}

	}


	private static Object getMessage(SpecificTicker ticker,SpecificTicker lastTicker) {
		JSONObject json = new JSONObject();
		json.put("currentTicker", new JSONObject(ticker));
		json.put("lastTicker", new JSONObject(lastTicker));
		return json;
	}


	private static String getChannel(SpecificTicker ticker) {
		return Arrays.asList(Symbol.values()).stream().filter(t->t.getSymbol().equals(ticker.getSymbol())).findFirst().get().getWebSocketChannel();
	}

}
