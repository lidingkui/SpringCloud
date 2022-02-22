package com.hubject.oembackend.config.request;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ExtractingResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Bean
    public HttpClientConnectionManager poolingHttpClientConnectionManager() {
        // 注册http和https请求
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(500);
        // 同路由并发数（每个主机的并发）
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);
        return poolingHttpClientConnectionManager;
    }

    @Bean
    public HttpClient httpClient(HttpClientConnectionManager poolingHttpClientConnectionManager) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 设置http连接管理器
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        try {
            SSLConnectionSocketFactory sslConnectionSocketFactory
                    = new SSLConnectionSocketFactory(createCustomerSSLContext());
            httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);

        } catch (Exception e) {
            throw new RuntimeException("无法构建 SSLConnectionSocketFactory! " + e.getMessage());
        }

        // 设置默认请求头
        /*List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        httpClientBuilder.setDefaultHeaders(headers);*/

        return httpClientBuilder.build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        // httpClient创建器
        clientHttpRequestFactory.setHttpClient(httpClient);
        // 连接超时时间/毫秒（连接上服务器(握手成功)的时间，超出抛出connect timeout）
        clientHttpRequestFactory.setConnectTimeout(5 * 1000);
        // 数据读取超时时间(socketTimeout)/毫秒（务器返回数据(response)的时间，超过抛出read timeout）
        clientHttpRequestFactory.setReadTimeout(60 * 1000);
        // 连接池获取请求连接的超时时间，不宜过长，必须设置/毫秒（超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool）
        clientHttpRequestFactory.setConnectionRequestTimeout(10 * 1000);

        return clientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate();
        // 可重复读流
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(clientHttpRequestFactory));

        // 请求拦截器
        restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptor());

        // 使用FastJson作消息转换
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        List<HttpMessageConverter<?>> converters = messageConverters.stream()
                .filter(item -> !(item instanceof MappingJackson2HttpMessageConverter))
                .collect(Collectors.toList());
        FastJsonHttpMessageConverter cvt = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        cvt.setFastJsonConfig(config);
        cvt.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(0, cvt);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }


    private SSLContext createCustomerSSLContext() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, CertificateException, UnrecoverableKeyException {
        SSLContext context = SSLContext.getInstance("TLS");
        KeyStore keyStore = getKeyStore(RestTemplateConfig.class.getResourceAsStream("/lotus.p12"), "123456");
        KeyManager[] kms = createKeyManager(keyStore, "123456");
        TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        context.init(kms, new TrustManager[]{tm}, null);
        return context;
    }

    private static KeyStore getKeyStore(InputStream stream, String password) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(stream, password.toCharArray());
        return keyStore;
    }

    private KeyManager[] createKeyManager(KeyStore keyStore, String password) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore, password.toCharArray());
        return factory.getKeyManagers();
    }
}
