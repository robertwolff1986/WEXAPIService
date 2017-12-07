package org.wolffr.wex.common.mongo.depth;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("depth")
public class SingleDepth {

	@Id
	private ObjectId id;
	private List<List<Double>> asks;
	private List<List<Double>> bids;
	private String symbol;

	public List<List<Double>> getAsks() {
		return asks;
	}

	public void setAsks(List<List<Double>> asks) {
		this.asks = asks;
	}

	public List<List<Double>> getBids() {
		return bids;
	}

	public void setBids(List<List<Double>> bids) {
		this.bids = bids;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
