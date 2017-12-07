package org.wolffr.wex.common.mongo.ticker;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class SpecificTicker implements Serializable {

	private static final long serialVersionUID = -5873247837220428719L;
	@Id
	private ObjectId id;
	private String last;
	private String sell;
	private String buy;
	private String updated;
	private volatile String symbol;

	public SpecificTicker(String last, String sell, String buy, String updated,String symbol) {
		super();
		this.last = last;
		this.sell = sell;
		this.buy = buy;
		this.setSymbol(symbol);
		this.updated = updated;
	}

	public SpecificTicker() {
	}

	@Override
	public String toString() {
		return String.format("symbol %s,id: %s, last: %s, sell: %s, buy: %s, updated: %s",getSymbol(), id, last, sell, buy,
				updated);
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getBuy() {
		return buy;
	}

	public void setBuy(String buy) {
		this.buy = buy;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buy == null) ? 0 : buy.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + ((sell == null) ? 0 : sell.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpecificTicker other = (SpecificTicker) obj;
		if (buy == null) {
			if (other.buy != null)
				return false;
		} else if (!buy.equals(other.buy))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		if (sell == null) {
			if (other.sell != null)
				return false;
		} else if (!sell.equals(other.sell))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	
	
}
