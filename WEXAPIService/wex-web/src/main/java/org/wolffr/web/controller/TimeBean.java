package org.wolffr.web.controller;

import java.util.TimeZone;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class TimeBean {

	public TimeZone getTimeZone() {
	    return TimeZone.getDefault();
	}
}
