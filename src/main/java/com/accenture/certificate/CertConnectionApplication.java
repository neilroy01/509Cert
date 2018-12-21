package com.accenture.certificate;

import com.accenture.certificate.config.WsClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.accenture.two.SoapConnector;
import com.service_now.mysoap.Execute;
import com.service_now.mysoap.ExecuteResponse;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import javax.xml.soap.MessageFactory;
import javax.xml.ws.Response;

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
        System.setProperty("proxySet", "true");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "8888");
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");

        return args -> {
            String name = "Sajal";//Default Name
            if(args.length>0){
                name = args[0];
            }

            Execute request = new Execute();
            LOGGER.info("RUNNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ExecuteResponse response = null;
            try {
                //test webservice template
                /*
                SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(
                        MessageFactory.newInstance());
                messageFactory.afterPropertiesSet();

                WebServiceTemplate webServiceTemplate = new WebServiceTemplate(
                        messageFactory);
                Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

                marshaller.setContextPath("com.service_now.mysoap");
                marshaller.afterPropertiesSet();

                webServiceTemplate.setMarshaller(marshaller);
                webServiceTemplate.afterPropertiesSet();


                Response response1 = (Response) webServiceTemplate
                        .marshalSendAndReceive(
                                "https://dev50463.service-now.com/MySOAP.do?SOAP", request);

                Response msg = (Response) response1;
                //end test
*/

                response =(ExecuteResponse) wsClientConfiguration.getSoapConnector()
                        .callWebService("https://dev50463.service-now.com/MySOAP.do?SOAP", request);
                LOGGER.info("Name : "+response);
            } catch (Exception e){
                LOGGER.error("Failed to execute webservice call", e);
            }

        };
    }

	
}
