package com.accenture.one;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.service_now.mysoap.Execute;
import com.service_now.mysoap.ExecuteResponse;


public class CountryServiceClient extends WebServiceGatewaySupport {
   public ExecuteResponse execute(){
      String uri = "https://dev50463.service-now.com/MySOAP.do?WSDL";
      Execute request = new Execute();

      ExecuteResponse response =(ExecuteResponse) getWebServiceTemplate()
         .marshalSendAndReceive(uri, request);
      return response;
   }
   

}