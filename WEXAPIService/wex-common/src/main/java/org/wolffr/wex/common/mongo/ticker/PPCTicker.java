package org.wolffr.wex.common.mongo.ticker;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("ppc_ticker")
@Indexes(@Index(value = "updated", fields = @Field("updated")))
public class PPCTicker extends SpecificTicker implements Serializable {

	private static final long serialVersionUID = -4580115233130089450L;
	private volatile static String symbol = "ppc_usd";

	public PPCTicker() {
		super();
	};

	public PPCTicker(String last, String sell, String buy, String updated) {
		super(last, sell, buy, updated, symbol);
	}
}
