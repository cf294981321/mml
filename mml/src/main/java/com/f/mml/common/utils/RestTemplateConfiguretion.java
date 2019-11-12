package com.f.mml.common.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguretion {

    @Bean
    public RestTemplate restTemplate(ClientHttpsRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);
        reInitMessageConverter(restTemplate);
        return restTemplate;

    }

    public static void reInitMessageConverter(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }
        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1, converter);
    }

    @Bean
    public ClientHttpsRequestFactory simpleClientHttpRequestFactory() {
        ClientHttpsRequestFactory  factory = new ClientHttpsRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);
        return factory;
    }

    class ClientHttpsRequestFactory extends SimpleClientHttpRequestFactory {

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod)
                throws IOException {
            if (connection instanceof HttpsURLConnection) {
                prepareHttpsConnection((HttpsURLConnection) connection);
            }
            super.prepareConnection(connection, httpMethod);
        }

        private void prepareHttpsConnection(HttpsURLConnection connection) {
            connection.setHostnameVerifier(new SkipHostnameVerifier());
            try {
                connection.setSSLSocketFactory(createSslSocketFactory());
            } catch (Exception ex) {
                // Ignore
            }
        }

        private SSLSocketFactory createSslSocketFactory() throws Exception {
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    if(true) {}
                }
                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    if(true) {}
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }

            }}, new java.security.SecureRandom());
            return context.getSocketFactory();
        }

        private class SkipHostnameVerifier implements HostnameVerifier {

            @Override
            public boolean verify(String requestedHost, SSLSession remoteServerSession) {
                System.out.println(requestedHost);
                System.out.println("+++++++++++++++++++++++++++");
                System.out.println(remoteServerSession.getPeerHost());
                System.out.println("+++++++++++++++++++++++++++++");
                System.out.println(requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost()));
                return requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost()); // Compliant
            }

        }

//        private class SkipX509TrustManager implements X509TrustManager {
//
//            @Override
//            public X509Certificate[] getAcceptedIssuers() {
//                return new X509Certificate[0];
//            }
//
//            @Override
//            public void checkClientTrusted(X509Certificate[] chain, String authType)   {
//            }
//
//            @Override
//            public void checkServerTrusted(X509Certificate[] chain, String authType) {
//            }
//
//        }
    }

}