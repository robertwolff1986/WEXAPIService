package org.wolffr.wex.api.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wolffr.wex.common.mongo.ticker.SingleTicker;

public class TickerUtilTest {
	
	private SingleTicker singleTicker;
	
	@BeforeEach
	public void init() {
		singleTicker= new SingleTicker();
		singleTicker.setAvg("3");
		singleTicker.setBuy("5");
		singleTicker.setHigh("2");
		singleTicker.setLow("0");
		singleTicker.setSell("2");
		singleTicker.setSymbol("btc_usd");
		singleTicker.setUpdated("1511369277");
		singleTicker.setVol("100");
		singleTicker.setVol_cur("1000");
	}

	@Test
	void testSetTickerTime() {
		TickerUtil.setTickerTime(singleTicker);
		assertThat(singleTicker.getUpdated(), equalTo("2017-11-22 17:47:57"));
	}
}
