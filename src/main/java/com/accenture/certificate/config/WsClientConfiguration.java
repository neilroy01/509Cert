package com.accenture.certificate.config;

import com.accenture.two.SoapConnector;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Configuration
public class WsClientConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(WsClientConfiguration.class);


    @Bean
    public SoapConnector getSoapConnector() throws Exception {
        SoapConnector client = new SoapConnector();
        client.setWebServiceTemplate(mwWebServiceTemplate());

        return client;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        // set security actions "Timestamp Signature Encrypt "
        securityInterceptor.setSecurementActions(
                WSHandlerConstants.TIMESTAMP + " " +
                        WSHandlerConstants.SIGNATURE);
        securityInterceptor.setSecurementUsername("snclient");
        securityInterceptor.setSecurementPassword("abcd1234");

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
        messageFactory.setPayloadCaching(true);
        return messageFactory;
    }

//    @Bean
//    public HttpsUrlConnectionMessageSender defaultMwMessageSender() throws Exception {
//        Resource keyStore = new ClassPathResource("snclient.jks");
//        KeyStore ks = KeyStore.getInstance("JKS");
//        ks.load(keyStore.getInputStream(), "abcd1234".toCharArray());
//
//        logger.info("Loaded keystore: " + keyStore.getURI().toString());
//        try {
//            keyStore.getInputStream().close();
//        } catch (IOException e) {
//        }
//        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        keyManagerFactory.init(ks, "abcd1234".toCharArray());
//
//        Resource trustStore = new ClassPathResource("truststore.jks");
//        KeyStore ts = KeyStore.getInstance("JKS");
//        ts.load(trustStore.getInputStream(), "abcd1234".toCharArray());
//        logger.info("Loaded trustStore: " + trustStore.getURI().toString());
//        trustStore.getInputStream().close();
//        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        trustManagerFactory.init(ts);
//
//        HttpsUrlConnectionMessageSender messageSender = new BasicAuthHttpsConnectionMessageSender(
//                "wss_integration_usr", "abcd1234");
//
//        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
////        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());
//        messageSender.setTrustManagers(new TrustManager[]{new UnTrustworthyTrustManager()});
//
//        // otherwise: java.security.cert.CertificateException: No name matching localhost found
//        messageSender.setHostnameVerifier((hostname, sslSession) -> {
//            return true;
//        });
//
//        return messageSender;
//    }

    @Bean
    public HttpComponentsMessageSender defaultMwMessageSender() {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setCredentials(new UsernamePasswordCredentials("wss_integration_usr", "abcd1234"));
        return messageSender;
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("abcd1234");
        cryptoFactoryBean.setKeyStoreLocation(
                new ClassPathResource("snclient.keystore"));
        return cryptoFactoryBean;
    }

    // You might need org.springframework.ws:spring-ws-support in order to
    // have HttpsUrlConnectionMessageSender
    public final class BasicAuthHttpsConnectionMessageSender extends HttpsUrlConnectionMessageSender {
        private String b64Creds;

        public BasicAuthHttpsConnectionMessageSender(String username, String password) {
            b64Creds = Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection) throws IOException {
            connection.setRequestProperty(HttpHeaders.AUTHORIZATION, String.format("Basic %s", b64Creds));
            super.prepareConnection(connection);
        }

    }

    class UnTrustworthyTrustManager
            implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}