package org.wolffr.wex.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.wolffr.wex.common.mongo.ticker.SingleTicker;

public class TickerUtil {

	/**
	 * Convert the update time to human readable form in format 'yyyy-MM-dd HH:mm:ss"'
	 * @param singleTicker
	 */
	public static void setTickerTime(SingleTicker singleTicker) {
		LocalDateTime localDateTime= LocalDateTime.now().minusSeconds((System.currentTimeMillis()/1000)-Long.valueOf(singleTicker.getUpdated()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		singleTicker.setUpdated(formatter.format(localDateTime));
	}
}
