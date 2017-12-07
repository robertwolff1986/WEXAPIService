package org.wolffr.wex.common.mongo.ticker;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("ticker")
@Indexes(@Index(value = "updated", fields = @Field("updated")))
public class SingleTicker {

	@Id
	private ObjectId id;
	private String high;
	private String low;
	private String avg;
	private String vol;
	private String vol_cur;
	private String last;
	private String sell;
	private String buy;
	private String updated;
	private String symbol;
	
	@Override
	public String toString()
	{
		return String.format("symbol %s,id: %s,high: %s, low: %s, avg: %s, vol: %s, vol_cur: %s, last: %s, sell: %s, buy: %s, updated: %s", symbol,id,high,low,avg,vol,vol_cur,last,sell,buy,updated);
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getVol() {
		return vol;
	}

	public void setVol(String vol) {
		this.vol = vol;
	}

	public String getVol_cur() {
		return vol_cur;
	}

	public void setVol_cur(String vol_cur) {
		this.vol_cur = vol_cur;
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
	
	
	
	

}
