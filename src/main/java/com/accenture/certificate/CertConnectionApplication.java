package com.accenture.certificate;

import com.accenture.certificate.config.WsClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.service_now.mysoap.Execute;
import com.service_now.mysoap.ExecuteResponse;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
@ComponentScan({"com.accenture"})
public class CertConnectionApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(CertConnectionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CertConnectionApplication.class, args);
	}
	
    @Bean
    CommandLineRunner lookup(WsClientConfiguration wsClientConfiguration) {
//        System.setProperty("proxySet", "true");
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "8888");
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8888");

        return args -> {
            Execute request = new Execute();
            LOGGER.info("RUNNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ExecuteResponse response = null;
            try {

                response =(ExecuteResponse) wsClientConfiguration.getSoapConnector()
                        .callWebService("https://dev50463.service-now.com/MySOAP.do?SOAP", request);
                LOGGER.info("Name : "+response);
            } catch (Exception e){
                LOGGER.error("Failed to execute webservice call", e);
            }

        };
    }

	
}
