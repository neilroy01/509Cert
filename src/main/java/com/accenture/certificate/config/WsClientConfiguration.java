package com.accenture.certificate.config;

import com.accenture.certificate.connector.SoapConnector;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.io.IOException;

@Configuration
public class WsClientConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(WsClientConfiguration.class);
    private static final String DIRECT_REFERENCE = "DirectReference";
    private static final String SKI_KEY_IDENTIFIER = "SKIKeyIdentifier";

    @Value("${client.keystore}")
    public String keyStore;

    @Value("${client.keystorepassword}")
    public String keyStorePassword;

    @Value("${client.keystorealias}")
    public String keyStoreAlias;

    @Value("${client.basicauthuser}")
    public String basicAuthUser;

    @Value("${client.basicauthpassword}")
    public String basicAuthPassword;

    @Bean
    public SoapConnector getSoapConnector() throws Exception {
        SoapConnector client = new SoapConnector();
        client.setWebServiceTemplate(mwWebServiceTemplate());

        return client;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        // set security actions "Timestamp Signature"


        securityInterceptor.setSecurementActions(
                WSHandlerConstants.TIMESTAMP + " " +
                        WSHandlerConstants.SIGNATURE + " " +
                        WSHandlerConstants.USERNAME_TOKEN);
        securityInterceptor.setSecurementUsername(basicAuthUser);
        securityInterceptor.setSecurementPassword(basicAuthPassword);
        securityInterceptor.setSecurementEncryptionKeyIdentifier(SKI_KEY_IDENTIFIER);
        securityInterceptor.setSecurementSignatureKeyIdentifier(DIRECT_REFERENCE);

        securityInterceptor.setSecurementSignatureUser(keyStoreAlias);

        // sign the request
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

        return securityInterceptor;
    }

    @Bean
    public WebServiceTemplate mwWebServiceTemplate() throws Exception {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.service_now.mysoap");
        WebServiceTemplate template = new WebServiceTemplate(marshaller, marshaller);
        template.setDefaultUri("https://dev50463.service-now.com/MySOAP.do?SOAP");
        template.setInterceptors(new ClientInterceptor[]{securityInterceptor()});
        template.setMessageSender(defaultMwMessageSender());
        template.setMessageFactory(messageFactory());

        return template;
    }

    @Bean
    AxiomSoapMessageFactory messageFactory() {
        AxiomSoapMessageFactory messageFactory = new AxiomSoapMessageFactory();
//        messageFactory.setSoapVersion(SoapVersion.SOAP_12);
        messageFactory.setPayloadCaching(true);
        return messageFactory;
    }

    @Bean
    public HttpComponentsMessageSender defaultMwMessageSender() {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setCredentials(new UsernamePasswordCredentials(basicAuthUser,
                basicAuthPassword));
        return messageSender;
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword(keyStorePassword);
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource(keyStore));
        cryptoFactoryBean.setDefaultX509Alias(keyStoreAlias);
        return cryptoFactoryBean;
    }

}