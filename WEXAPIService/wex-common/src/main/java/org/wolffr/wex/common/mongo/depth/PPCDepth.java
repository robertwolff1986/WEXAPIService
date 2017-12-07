package org.wolffr.wex.common.mongo.depth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("ppc_depth")
@Indexes(@Index(fields = @Field("updated")))
public class PPCDepth extends SpecificDepth implements Serializable {

	private static final long serialVersionUID = -9150226354336037120L;
	private volatile static String symbol = "ppc_usd";

	public PPCDepth() {
		super();
	};

	public PPCDepth(List<List<Double>> asks,List<List<Double>> bids, String buy, String updated) {
		super(asks,bids,updated,symbol);
	}
}
