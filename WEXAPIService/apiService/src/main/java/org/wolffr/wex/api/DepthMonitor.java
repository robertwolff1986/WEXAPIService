package org.wolffr.wex.api;

import java.io.IOException;

import org.bson.json.JsonParseException;
import org.json.JSONObject;
import org.wolffr.wex.common.mongo.depth.SingleDepth;
import org.wolffr.wex.mongo.DepthStore;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DepthMonitor {

	private DepthStore depthStore;
	private String symbol;

	public DepthMonitor(String symbol, DepthStore depthStore) {
		System.out.println("Created DepthMonitor with symbol: " + symbol);
		this.symbol = symbol;
		this.depthStore = depthStore;
	}

	public void monitor()  {
		try {
			Thread.sleep(2000);
			consumeDepth(getCurrentDepth());
			monitor();
		} catch (Exception e) {
			e.printStackTrace();
			monitor();
		}
	}

	private void consumeDepth(String depthJSON) {
		try {
			SingleDepth specificDepth = createSpecificDepth(depthJSON);
			depthStore.process(specificDepth);
		} catch (JsonParseException | IOException e) {
			System.out.println("Could not consume ticker: " + e.getStackTrace());
		}
	}

	private String getCurrentDepth() {
		try {
			return PublicApi.getCurrentDepth(symbol);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private SingleDepth createSpecificDepth(String depthJSON)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		SingleDepth depth = mapper.readValue(new JSONObject(depthJSON).getJSONObject(symbol).toString(),
				SingleDepth.class);
		depth.setSymbol(symbol);
		return depth;
	}
}
