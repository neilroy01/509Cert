package com.example.one;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.service_now.mysoap.ExecuteResponse;


public class MainApp {
   public static void main(String[] args) {
	   
	   
	   
	   
      CountryServiceClient client = new CountryServiceClient();
      Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
      marshaller.setContextPath("com.service_now.mysoap");
      client.setMarshaller(marshaller);
      client.setUnmarshaller(marshaller);
      ExecuteResponse response = client.execute();

      System.out.println("Country : " + response.getResult());
   }
}