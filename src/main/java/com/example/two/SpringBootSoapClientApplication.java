package com.example.two;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.service_now.mysoap.Execute;
import com.service_now.mysoap.ExecuteResponse;

//@SpringBootApplication
//@ComponentScan({"com.example.two"})
//@EntityScan("com.example.two")
public class SpringBootSoapClientApplication {
 /*
    public static void java.main(String[] args) {
        SpringApplication.run(SpringBootSoapClientApplication.class, args);
    }
     
    
    @Bean
    CommandLineRunner lookup(SoapConnector soapConnector) {
    return args -> {
        String name = "Sajal";//Default Name
        if(args.length>0){
            name = args[0];
        }
        Execute request = new Execute();
        ExecuteResponse response =(ExecuteResponse) soapConnector.callWebService("http://www.service-now.com/MySOAP/execute", request);
        System.out.println("Got Response As below ========= : ");
        System.out.println("Name : "+response.getResult());
    	};
    }*/
}