package org.wolffr.web.controller;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wolffr.web.SimpleTickerDataStore;

@ManagedBean
@ApplicationScoped
public class WexWebController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WexWebController.class);

	@EJB
	private SimpleTickerDataStore simpleTickerDataStore;

	public SimpleTickerDataStore getSimpleTickerDataStore() {
		return simpleTickerDataStore;
	}
}
