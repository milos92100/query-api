package org.queryapi.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ElasticConfiguration {

    @Value("${elastic.host}")
    private String host;

    @Value("${elastic.port}")
    private Integer port;

    @Value("${elastic.scheme}")
    private String scheme;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RestHighLevelClient elasticClient() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost(host, port, scheme)
        ));
    }
}
