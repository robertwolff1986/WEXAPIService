package org.wolffr.wex.mongo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.wolffr.wex.api.util.TickerUtil;
import org.wolffr.wex.common.TickerMessageSender;
import org.wolffr.wex.common.mongo.ticker.SingleTicker;
import org.wolffr.wex.common.mongo.ticker.SpecificTicker;
import org.wolffr.wex.mongo.factory.SpecificTickerFactory;

@Stateless
public class TickerStore {

	@EJB
	MongoConnection mongoConnection;

	private boolean store(Object object) {
		mongoConnection.getDatastore().save(object);
		return true;
	}

	public void process(SingleTicker singleTicker) {
		TickerUtil.setTickerTime(singleTicker);
		store(singleTicker);
		SpecificTicker specificTicker = createSpecificTicker(singleTicker);
		store(specificTicker);
		TickerMessageSender.send(specificTicker);
	}

	private SpecificTicker createSpecificTicker(SingleTicker singleTicker) {
		return (SpecificTicker) SpecificTickerFactory.createSpecificTicker(singleTicker);
	}

}
