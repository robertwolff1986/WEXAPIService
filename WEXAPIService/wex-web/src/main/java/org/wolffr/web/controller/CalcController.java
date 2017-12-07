package org.wolffr.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CalcController {

	private Double wexInput;
	private Double wexFee;
	private Double minWexSellToProfit;
	private static final Double WEXTRADINGFEE = 0.004;

	public Double getWexInput() {
		return wexInput;
	}

	public void setWexInput(Double wexInput) {
		this.wexInput = wexInput;
		if (wexInput != null) {
			wexFee = wexInput * WEXTRADINGFEE;
			minWexSellToProfit = wexInput + wexFee;
		} else {
			wexFee = 0.0;
			minWexSellToProfit = 0.0;
		}
	}

	public Double getWexFee() {
		return wexFee;
	}

	public Double getMinWexSellToProfit() {
		return minWexSellToProfit;
	}
}
