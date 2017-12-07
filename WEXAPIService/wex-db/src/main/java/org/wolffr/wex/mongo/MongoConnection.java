package org.wolffr.wex.mongo;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoConnection {
		
	private MongoClient mongoClient = null;
	private Datastore datastore;
		
	@Lock(LockType.READ)
	public MongoClient getMongoClient(){	
		return mongoClient;
	}
	
	public Datastore getDatastore()
	{
		return datastore;
	}
	
	@PostConstruct
	public void init() {
		initMongoDb();
		initMorphia();
	}

	private void initMongoDb() {
		String mongoIpAddress = "127.0.0.1";
		Integer mongoPort = 27017;
		mongoClient = new MongoClient(mongoIpAddress, mongoPort);
	}

	private void initMorphia() {
		final Morphia morphia = new Morphia();
		morphia.mapPackage("org.wolff.wex");
		datastore = morphia.createDatastore(mongoClient, "wex");
		datastore.ensureIndexes();
	}
		
}