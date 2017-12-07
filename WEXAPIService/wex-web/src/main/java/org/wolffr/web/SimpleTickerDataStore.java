package org.wolffr.web;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.faces.bean.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wolffr.web.model.WebSpecificTicker;
import org.wolffr.wex.common.mongo.ticker.SpecificTicker;

@Singleton
@ApplicationScoped
public class SimpleTickerDataStore {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTickerDataStore.class);

	Map<String, WebSpecificTicker> currentTicker = new HashMap<>();

	public void processTicker(SpecificTicker ticker) {
		boolean changed = checkNotificationNeccessary(ticker);
		if (changed) {
			//LOGGER.info("changed");
			if (currentTicker.get(ticker.getSymbol()) != null
					&& currentTicker.get(ticker.getSymbol()).getNewTicker() != null) {
				//LOGGER.info("" + currentTicker.get(ticker.getSymbol()).getNewTicker());
				//LOGGER.info("" + ticker);
			}
			currentTicker.put(ticker.getSymbol(), createWebTicker(ticker));
			WebsocketConnector.sendNotification(ticker,currentTicker.get(ticker.getSymbol()).getLastTicker());
		}
	}

	private WebSpecificTicker createWebTicker(SpecificTicker ticker) {
		if (currentTicker.containsKey(ticker.getSymbol()))
			return new WebSpecificTicker(ticker, currentTicker.get(ticker.getSymbol()).getNewTicker());
		return new WebSpecificTicker(ticker, null);
	}

	private boolean checkNotificationNeccessary(SpecificTicker ticker) {
		if (!currentTicker.containsKey(ticker.getSymbol()))
			return true;
		return !currentTicker.get(ticker.getSymbol()).getNewTicker().equals(ticker);
	}

	public WebSpecificTicker getCurrentTickerBySymbol(String symbol) {
		return currentTicker.entrySet().stream().filter(e -> symbol.equals(e.getKey())).findAny().map(e -> e.getValue())
				.orElse(new WebSpecificTicker(null, null));
	}

}
