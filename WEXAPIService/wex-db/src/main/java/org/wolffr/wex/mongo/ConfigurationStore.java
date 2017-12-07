package org.wolffr.wex.mongo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.wolffr.wex.common.mongo.Configuration;

@Stateless
public class ConfigurationStore {

	@EJB
	MongoConnection mongoConnection;

	public Configuration getConfiguration() {
		try {
			Configuration configuration = (Configuration) mongoConnection.getDatastore().find(Configuration.class)
					.get();
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
