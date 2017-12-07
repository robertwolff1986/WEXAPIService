package org.wolffr.wex.mongo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.wolffr.wex.common.DepthMessageSender;
import org.wolffr.wex.common.mongo.depth.SingleDepth;
import org.wolffr.wex.common.mongo.depth.SpecificDepth;
import org.wolffr.wex.mongo.factory.SpecificDepthFactory;

@Stateless
public class DepthStore {

	@EJB
	MongoConnection mongoConnection;
	private static Map<String, String> lastDepthHash = new ConcurrentHashMap<>();

	private boolean store(Object object) {
		mongoConnection.getDatastore().save(object);
		return true;
	}

	public void process(SingleDepth singleDepth) {

		SpecificDepth specificDepth = createSpecificDepth(singleDepth);
		if (isNewDepthData(specificDepth)){
			specificDepth.setUpdated(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
			store(specificDepth);
		}
		 DepthMessageSender.send(specificDepth);
	}

	private boolean isNewDepthData(SpecificDepth specificDepth) {
		String currentHash = specificDepth.getAsks().hashCode() + "" + specificDepth.getBids().hashCode();
		if (!lastDepthHash.containsKey(specificDepth.getSymbol())) {
			lastDepthHash.put(specificDepth.getSymbol(), currentHash);
			return true;
		}
		if (currentHash.equals(lastDepthHash.get(specificDepth.getSymbol())))
			return false;
		lastDepthHash.put(specificDepth.getSymbol(), currentHash);
		return true;
	}

	private SpecificDepth createSpecificDepth(SingleDepth singleDepth) {
		return (SpecificDepth) SpecificDepthFactory.createSpecificDepth(singleDepth);
	}

}
