package org.wolffr.wex.common.mongo.ticker;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("btc_ticker")
@Indexes(@Index(value = "updated", fields = @Field("updated")))
public class BTCTicker extends SpecificTicker implements Serializable {

	private static final long serialVersionUID = -9150226354336037120L;
	private volatile static String symbol = "btc_usd";

	public BTCTicker() {
		super();
	};

	public BTCTicker(String last, String sell, String buy, String updated) {
		super(last, sell, buy, updated, symbol);
	}
}
