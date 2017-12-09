package org.wolffr.wex.api;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
		for (Symbol symbol : Arrays.asList(Symbol.values())) {
			ScheduledExecutorService tickerExecutorService = Executors.newScheduledThreadPool(1);
			tickerExecutorService.scheduleAtFixedRate(createTickerTask(symbol.getSymbol()), 5, 5, TimeUnit.SECONDS);
			ScheduledExecutorService depthExecutorService = Executors.newScheduledThreadPool(1);
			depthExecutorService.scheduleAtFixedRate(crateDepthTask(symbol.getSymbol()), 5, 5, TimeUnit.SECONDS);
		}
	}

	private Runnable createTickerTask(String symbol) {
		Runnable task = () -> {
			new CoinMonitor(symbol, tickerStore).monitor();
		};
		return task;
	}

	private Runnable crateDepthTask(String symbol) {
		Runnable task = () -> {
				new DepthMonitor(symbol, depthStore).monitor();
		};
		return task;
	}
}
