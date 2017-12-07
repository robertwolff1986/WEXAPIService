package org.wolffr.web.model;

import org.wolffr.wex.common.mongo.ticker.SpecificTicker;

public class WebSpecificTicker {

	public WebSpecificTicker(SpecificTicker newTicker, SpecificTicker lastTicker) {
		super();
		this.newTicker = newTicker;
		this.lastTicker = lastTicker;
	}

	private SpecificTicker newTicker;
	private SpecificTicker lastTicker;

	public SpecificTicker getNewTicker() {
		return newTicker;
	}

	public void setNewTicker(SpecificTicker newTicker) {
		this.newTicker = newTicker;
	}

	public SpecificTicker getLastTicker() {
		return lastTicker;
	}

	public void setLastTicker(SpecificTicker lastTicker) {
		this.lastTicker = lastTicker;
	}

	public Double buyIncrease() {
		if (newTicker != null && newTicker.getBuy() != null && lastTicker != null && lastTicker.getBuy() != null)
			return calcIncrease(newTicker.getBuy(), lastTicker.getBuy());
		return Double.valueOf(0);
	}

	public Double sellIncrease() {
		if (newTicker != null && newTicker.getSell() != null && lastTicker != null && lastTicker.getSell() != null)
			return calcIncrease(newTicker.getSell(), lastTicker.getSell());
		return Double.valueOf(0);
	}

	public Double lastIncrease() {
		if (newTicker != null && newTicker.getLast() != null && lastTicker != null && lastTicker.getLast() != null)
			return calcIncrease(newTicker.getLast(), lastTicker.getLast());
		return Double.valueOf(0);
	}

	private Double calcIncrease(String current, String last) {
		return Double.valueOf(last) - Double.valueOf(current);
	}

}
