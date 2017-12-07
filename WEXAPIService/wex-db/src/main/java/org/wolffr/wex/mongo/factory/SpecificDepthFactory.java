package org.wolffr.wex.mongo.factory;

import org.wolffr.wex.common.Symbol;
import org.wolffr.wex.common.mongo.depth.SingleDepth;
import org.wolffr.wex.common.mongo.depth.SpecificDepth;;

public class SpecificDepthFactory {

	public static Object createSpecificDepth(SingleDepth singleDepth) {
		SpecificDepth specificDepth = Symbol.getSpecificDepthBySymbol(singleDepth.getSymbol());
		specificDepth.setBids(singleDepth.getBids());
		specificDepth.setAsks(singleDepth.getAsks());
		specificDepth.setSymbol(singleDepth.getSymbol());
		return specificDepth;
	}
}
