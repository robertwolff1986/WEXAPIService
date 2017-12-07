package org.wolffr.wex.mongo.factory;

import org.wolffr.wex.common.Symbol;
import org.wolffr.wex.common.mongo.ticker.SingleTicker;
import org.wolffr.wex.common.mongo.ticker.SpecificTicker;;

public class SpecificTickerFactory {

	public static Object createSpecificTicker(SingleTicker singleTicker) {
		SpecificTicker ticker = Symbol.getSpecificTickerBySymbol(singleTicker.getSymbol());
		ticker.setLast(singleTicker.getLast());
		ticker.setSell(singleTicker.getSell());
		ticker.setBuy(singleTicker.getBuy());
		ticker.setUpdated(singleTicker.getUpdated());
		ticker.setSymbol(singleTicker.getSymbol());
		return ticker;
	}
}
