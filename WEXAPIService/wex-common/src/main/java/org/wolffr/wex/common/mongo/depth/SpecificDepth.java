package org.wolffr.wex.common.mongo.depth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class SpecificDepth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8888872403345703565L;
	@Id
	private ObjectId id;
	private List<List<Double>> bids;
	private List<List<Double>> asks;
	private String updated;
	private volatile String symbol;

	public SpecificDepth(List<List<Double>> asks, List<List<Double>> bids, String updated, String symbol) {
		super();
		this.asks = asks;
		this.bids = bids;
		this.updated = updated;
		this.setSymbol(symbol);
	}

	public SpecificDepth() {
	}

	public List<List<Double>> getBids() {
		return bids;
	}

	public void setBids(List<List<Double>> bids) {
		this.bids = bids;
	}

	public List<List<Double>> getAsks() {
		return asks;
	}

	public void setAsks(List<List<Double>> asks) {
		this.asks = asks;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
