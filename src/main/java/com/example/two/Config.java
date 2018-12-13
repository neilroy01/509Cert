package com.example.two;

import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
public class Config {

    /*
    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        // set security actions on request
        securityInterceptor.setSecurementActions("Signature");
        // sign the request
        securityInterceptor.setSecurementUsername(signingCertName);
        securityInterceptor.setSecurementPassword(signingCertPassword);
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setSecurementSignatureKeyIdentifier("DirectReference");
        securityInterceptor.setValidationActions("NoSecurity");

        // no validation for the response
        securityInterceptor.setValidateResponse(false);
        return securityInterceptor;
    }*/
    
    
    
	@Bean
	public Jaxb2Marshaller marshaller() {
		System.out.println("RUNNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!3");
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("com.service_now.mysoap");

		return marshaller;
	}

	
	 @Bean
	    public SoapConnector soapConnector(Jaxb2Marshaller marshaller)  throws Exception {
		 System.out.println("RUNNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!4");
		 SoapConnector client = new SoapConnector();
	        client.setDefaultUri("https://dev50463.service-now.com/MySOAP.do?SOAP");
	        client.setMarshaller(marshaller);
	        client.setUnmarshaller(marshaller);
	       //client.setMessageFactory(getMessageFactory())
	        client.setMessageSender(defaultMwMessageSender());
	       // client.setMessageSender(getWebServiceMessageSender());
	        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor()};
	        client.setInterceptors(interceptors);
	        System.out.println("RUNNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!5");
	        return client;
	    }

	   @Bean
	    public HttpComponentsMessageSender defaultMwMessageSender() {
	        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
	        messageSender.setCredentials(new UsernamePasswordCredentials("wss_integration_usr", "abcd1234"));
	        return messageSender;
	    }
	   
	   @Bean
	   public WebServiceMessageFactory getMessageFactory() throws SOAPException {
	        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
	        SOAPMessage message = messageFactory.createMessage();
	        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        envelope.removeNamespaceDeclaration("env");
	        envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
	        SaajSoapMessageFactory saajSoapMessageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
	        saajSoapMessageFactory.setMessageFactory(messageFactory);
	        return saajSoapMessageFactory;
	    }
	   
	   /*
	   @Bean
	    private WebServiceMessageSender getWebServiceMessageSender() throws Exception {
	        KeyStore ts = KeyStore.getInstance("JKS");
	        ts.load(trustStore.getInputStream(), trustStorePassword.toCharArray());

	        KeyStore ks = KeyStore.getInstance("JKS");
	        ks.load(clientKeyStore.getInputStream(), clientKeyStorePassword.toCharArray());

	        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	        trustManagerFactory.init(ts);

	        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	        keyManagerFactory.init(ks, clientCertPassword.toCharArray());


	        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
	        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());
	        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
	        return messageSender;
	    }
	   */

	    @Bean
	    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
	        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

	        // set security actions
	        securityInterceptor.setSecurementActions("Timestamp Signature Encrypt");

	        // sign the request
	        //securityInterceptor.setSecurementUsername("client");
	        //securityInterceptor.setSecurementPassword("changeit");
	        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

	        // encrypt the request
	        /*
	        securityInterceptor.setSecurementEncryptionUser("server-public");
	        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());
	        securityInterceptor.setSecurementEncryptionParts("{Content}{https://memorynotfound.com/beer}getBeerRequest");
	        securityInterceptor.setSecurementSignatureKeyIdentifier("DirectReference");
*/
	        return securityInterceptor;
	    }
	    
	    @Bean
	    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
	        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
	        cryptoFactoryBean.setKeyStorePassword("abcd1234");
	        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("snclient.keystore"));
	        return cryptoFactoryBean;
	    }
	 
	 
}