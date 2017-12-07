package org.wolffr.wex.common.mongo.ticker;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("nvc_ticker")
@Indexes(@Index(value = "updated", fields = @Field("updated")))
public class NVCTicker extends SpecificTicker implements Serializable{

	private static final long serialVersionUID = 1594125392727340343L;
	private volatile static String symbol="nvc_usd";
	
	public NVCTicker() {
		super();
	};

	public NVCTicker(String last, String sell, String buy, String updated) {
		super(last, sell, buy, updated,symbol);
	}
}
