package org.wolffr.wex.common.mongo.ticker;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("ltc_ticker")
@Indexes(@Index(value = "updated", fields = @Field("updated")))
public class LTCTicker extends SpecificTicker implements Serializable {

	private static final long serialVersionUID = -5465875631006882889L;
	private volatile static String symbol = "ltc_usd";

	public LTCTicker() {
		super();
	};

	public LTCTicker(String last, String sell, String buy, String updated) {
		super(last, sell, buy, updated, symbol);
	}
}
