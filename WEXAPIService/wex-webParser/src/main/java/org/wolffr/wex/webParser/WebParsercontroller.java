package org.wolffr.wex.webParser;

import java.io.IOException;

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
		Thread boxParserThread= new Thread(parseTrollbox());
		boxParserThread.start();
	}
	
	private Runnable parseTrollbox() {
		Runnable task = () -> {
			try {
				boxParser.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		};
		return task;
	}
}
