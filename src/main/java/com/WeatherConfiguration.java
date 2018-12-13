package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.WebServiceMessageSender;

@Configuration
public class WeatherConfiguration {
/*
    private String signingCertName;
    private String signingCertPassword;
    private Resource signingKeyStore;
    private String signingKeyStorePassword;

    private Resource clientKeyStore;
    private String clientKeyStorePassword;
    private String clientCertPassword;

    private Resource trustStore;
    private String trustStorePassword;

    private Resource requestSchema;

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
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword(signingKeyStorePassword);
        cryptoFactoryBean.setKeyStoreLocation(signingKeyStore);
        return cryptoFactoryBean;
    }

    @Bean
    public PayloadValidatingInterceptor payloadValidatingInterceptor(Resource requestSchema) {
        // schema validation the request payload
        PayloadValidatingInterceptor payloadValidatingInterceptor = new PayloadValidatingInterceptor();
        payloadValidatingInterceptor.setValidateRequest(true);
        payloadValidatingInterceptor.setSchema(requestSchema);
        return payloadValidatingInterceptor;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.weather");
        return marshaller;
    }

    @Bean
    public WeatherClient weatherClient(Jaxb2Marshaller marshaller) throws Exception {
		WeatherClient client = new WeatherClient();
		client.setDefaultUri("http://wsf.cdyne.com/WeatherWS/Weather.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);   
        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor(), new AcknowledgementSoapResponseInterceptor(), payloadValidatingInterceptor(requestSchema)};
        client.setInterceptors(interceptors);
        client.setMessageFactory(getMessageFactory());
        client.setMessageSender(getWebServiceMessageSender());
        return client;
    }

    private WebServiceMessageFactory getMessageFactory() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        envelope.removeNamespaceDeclaration("env");
        envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
        SaajSoapMessageFactory saajSoapMessageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
        saajSoapMessageFactory.setMessageFactory(messageFactory);
        return saajSoapMessageFactory;
    }

    private WebServiceMessageSender getWebServiceMessageSender() throws Exception {
        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(trustStore.getInputStream(), trustStorePassword.toCharArray());
        LOG.info("Loaded trustStore: " + trustStore.getURI().toString());
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(clientKeyStore.getInputStream(), clientKeyStorePassword.toCharArray());
        LOG.info("Loaded client keystore: " + clientKeyStore.getURI().toString());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(ts);
        LOG.info("Initialised TrustManagerFactory");
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(ks, clientCertPassword.toCharArray());
        LOG.info("Initialised KeyManagerFactory");

        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());
        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        return messageSender;
    }*/
}
