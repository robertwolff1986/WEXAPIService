package org.wolffr.wex.api;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.wolffr.wex.common.Symbol;
import org.wolffr.wex.mongo.DepthStore;
import org.wolffr.wex.mongo.TickerStore;

@Singleton
@Startup
public class MainMonitor {

	@EJB
	private TickerStore tickerStore;
	@EJB
	private DepthStore depthStore;

	@PostConstruct
	private void init() {
		for(Symbol symbol : Arrays.asList(Symbol.values())) {
			Thread tickerThread = new Thread(createTickerTask(symbol.getSymbol()));
			tickerThread.start();
			Thread depthThread = new Thread(crateDepthTask(symbol.getSymbol()));
			depthThread.start();
		}
	}

	private Runnable createTickerTask(String symbol) {
		Runnable task = () -> {
			try {
				new CoinMonitor(symbol, tickerStore).monitor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		return task;
	}
	
	private Runnable crateDepthTask(String symbol) {
		Runnable task = () -> {
			try {
				new DepthMonitor(symbol, depthStore).monitor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		return task;
	}
}
