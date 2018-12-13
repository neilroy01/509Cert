package com.example.mit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.example.two.SoapConnector;
import com.service_now.mysoap.Execute;
import com.service_now.mysoap.ExecuteResponse;

@SpringBootApplication
@ComponentScan({"com.example.two"})
public class MitApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(MitApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MitApplication.class, args);
	}
	
    @Bean
    CommandLineRunner lookup(SoapConnector soapConnector) {
        System.setProperty("javax.net.debug", "all");
        //System.setProperty("javax.net.debug", "ssl");
        //System.setProperty("javax.net.ssl.keyStoreType", "jks");
        System.setProperty("javax.net.ssl.keyStore", "snclient.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "abcd1234");
        //System.setProperty("javax.net.ssl.trustStoreType", "jks");
        System.setProperty("javax.net.ssl.trustStore", "c:\\apps\\Java\\jre-10\\lib\\security\\cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        return args -> {
            String name = "Sajal";//Default Name
            if(args.length>0){
                name = args[0];
            }

            Execute request = new Execute();
            LOGGER.info("RUNNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ExecuteResponse response = null;
            try {
                response =(ExecuteResponse) soapConnector.callWebService("https://dev50463.service-now.com/MySOAP.do?SOAP", request);
                LOGGER.info("Name : "+response.getResult());
            } catch (Exception e){
                LOGGER.error("Failed to execute webservice call", e);
            }

        };
    }

	
}
