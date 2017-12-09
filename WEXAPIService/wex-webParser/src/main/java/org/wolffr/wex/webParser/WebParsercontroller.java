package org.wolffr.wex.webParser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class WebParsercontroller {
	
	
	@EJB
	private BoxParser boxParser;
	
	@PostConstruct
	public void init() throws Exception {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleAtFixedRate(parseTrollbox(), 5, 5, TimeUnit.SECONDS);
	}
	
	private Runnable parseTrollbox() {
		Runnable task = () -> {
				boxParser.run();
		};
		return task;
	}
}
