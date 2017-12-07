package org.wolffr.wex.common;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.wolffr.wex.common.mongo.depth.BTCDepth;
import org.wolffr.wex.common.mongo.depth.LTCDepth;
import org.wolffr.wex.common.mongo.depth.NMCDepth;
import org.wolffr.wex.common.mongo.depth.NVCDepth;
import org.wolffr.wex.common.mongo.depth.PPCDepth;
import org.wolffr.wex.common.mongo.depth.SpecificDepth;
import org.wolffr.wex.common.mongo.ticker.BTCTicker;
import org.wolffr.wex.common.mongo.ticker.LTCTicker;
import org.wolffr.wex.common.mongo.ticker.NMCTicker;
import org.wolffr.wex.common.mongo.ticker.NVCTicker;
import org.wolffr.wex.common.mongo.ticker.PPCTicker;
import org.wolffr.wex.common.mongo.ticker.SpecificTicker;

public enum Symbol {
	LTCUSD("ltc_usd", LTCTicker.class, LTCDepth.class, "/updateLTCTrade"), NVCUSD("nvc_usd", NVCTicker.class,
			NVCDepth.class, "/updateNVCTrade"), BTCUSD("btc_usd", BTCTicker.class, BTCDepth.class,
					"/updateBTCTrade"), PPCUSD("ppc_usd", PPCTicker.class, PPCDepth.class,
							"/updatePPCTrade"), NMCUSD("nmc_usd", NMCTicker.class, NMCDepth.class, "/updateNMCTrade");

	private String symbol;
	private String webSocketChannel;
	private Class<? extends SpecificTicker> tickerEntity;
	private Class<? extends SpecificDepth> depthEntity;

	Symbol(String symbol, Class<? extends SpecificTicker> tickerEntity, Class<? extends SpecificDepth> depthEntity,
			String webSocketChannel) {
		this.symbol = symbol;
		this.tickerEntity = tickerEntity;
		this.depthEntity = depthEntity;
		this.webSocketChannel = webSocketChannel;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getWebSocketChannel() {
		return webSocketChannel;
	}

	private Object getSpecificTickerEntity() {
		try {
			return tickerEntity.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Object getSpecificDepthEntity() {
		try {
			return depthEntity.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Symbol getSymbol(String symbol) {
		return Arrays.asList(Symbol.values()).stream().filter(t -> t.getSymbol().equals(symbol)).findFirst().get();
	}

	public static SpecificTicker getSpecificTickerBySymbol(String symbol) {
		return (SpecificTicker) getSymbol(symbol).getSpecificTickerEntity();
	}

	public static SpecificDepth getSpecificDepthBySymbol(String symbol) {
		return (SpecificDepth) getSymbol(symbol).getSpecificDepthEntity();
	}

}
