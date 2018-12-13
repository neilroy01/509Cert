package com.example.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SoapConnector extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(SoapConnector.class);

	
    public Object callWebService(String url, Object request){
    	System.out.println("RUNNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!2");
        return getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
	
}
