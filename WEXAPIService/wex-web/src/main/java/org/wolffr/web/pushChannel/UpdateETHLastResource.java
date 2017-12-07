package org.wolffr.web.pushChannel;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/updateETHLast")
public class UpdateETHLastResource {

    @OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(String data) {
        return data;
    }
} 
