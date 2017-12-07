package org.wolffr.wex.common.mongo.ticker;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("nmc_ticker")
@Indexes(@Index(value = "updated", fields = @Field("updated")))
public class NMCTicker extends SpecificTicker implements Serializable {

	private static final long serialVersionUID = 5557431351632615835L;
	private volatile static String symbol = "nmc_usd";

	public NMCTicker() {
		super();
	};

	public NMCTicker(String last, String sell, String buy, String updated) {
		super(last, sell, buy, updated, symbol);
	}
}
