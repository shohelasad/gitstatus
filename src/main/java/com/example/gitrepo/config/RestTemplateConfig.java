package com.example.gitrepo.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {
    @Value("${pool.size}")
    private int poolSize;

    @Value("${timeout.ms}")
    private int timeout;

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClients.custom()
                .setConnectionManager(poolingConnectionManager())
                .build();
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(poolSize);
        poolingConnectionManager.setDefaultMaxPerRoute(poolSize);
        return poolingConnectionManager;
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        requestFactory.setConnectTimeout(timeout);
        requestFactory.setConnectionRequestTimeout(timeout);
        return new RestTemplate(requestFactory);
    }
}