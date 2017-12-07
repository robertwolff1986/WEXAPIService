package org.wolffr.wex.analyzer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.wolffr.wex.common.mongo.ticker.SpecificTicker;

@Singleton
public class TickerAnalyzer {
	
	@EJB
	private MailService mailService;

	private static final Double oneMinuteSwingPercentToNotify = 2.0;
	private static final Double tenMinuteSwingPercentToNotify = 8.0;
	Map<String, List<SpecificTicker>> tenMinuteTickerMap = new ConcurrentHashMap<>();
	Map<String, List<SpecificTicker>> oneMinuteTickerMap = new ConcurrentHashMap<>();

	public void analyzeTicker(SpecificTicker ticker) {
		addTickerToTickerMap(tenMinuteTickerMap, ticker);
		addTickerToTickerMap(oneMinuteTickerMap, ticker);
		checkPriceSwing(oneMinuteTickerMap.get(ticker.getSymbol()), oneMinuteSwingPercentToNotify);
		checkPriceSwing(tenMinuteTickerMap.get(ticker.getSymbol()), tenMinuteSwingPercentToNotify);
	}

	private void checkPriceSwing(List<SpecificTicker> tickerList, Double swingToNotify) {
		SpecificTicker currentTicker = tickerList.get(tickerList.size() - 1);
		SpecificTicker oldestTicker = tickerList.get(0);

		Double percentSwing = (Double.valueOf(currentTicker.getLast()) - Double.valueOf(oldestTicker.getLast()))
				/ (Double.valueOf(oldestTicker.getLast()) / 100.0);
		if(Math.abs(percentSwing)>swingToNotify)
			sendNotificationMail(currentTicker.getSymbol(), percentSwing, oldestTicker.getLast(),currentTicker.getLast());
	}

	private void sendNotificationMail(String symbol, Double percentSwing, String old, String current) {
		mailService.sendPercentSwingMail(symbol,percentSwing,old,current);
	}

	private void addTickerToTickerMap(Map<String, List<SpecificTicker>> tickerMap, SpecificTicker ticker) {
		if (tickerMap.get(ticker.getSymbol()) == null) {
			List<SpecificTicker> tickerList = new ArrayList<>();
			tickerList.add(ticker);
			tickerMap.put(ticker.getSymbol(), tickerList);
		} else {
			tickerMap.get(ticker.getSymbol()).add(ticker);
		}
		clearTickerMap(tickerMap);
	}

	private void clearTickerMap(Map<String, List<SpecificTicker>> tickerMap) {
		tickerMap.entrySet().stream().forEach(entry -> entry.setValue(
				entry.getValue().stream().filter(this::checkUpdateTimeInInteral).collect(Collectors.toList())));
	}

	private boolean checkUpdateTimeInInteral(SpecificTicker ticker) {
		LocalDateTime time = LocalDateTime.parse(ticker.getUpdated(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return time.isAfter(LocalDateTime.now().minusMinutes(10));
	}

}
