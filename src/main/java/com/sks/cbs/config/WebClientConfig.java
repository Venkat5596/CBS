package com.sks.cbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public static final String BASE_URL = "https://ccapi-ipacc.cbsa-asfc.cloud-nuage.canada.ca/v1/tariff-srv";

//    @Bean
//   public RestTemplate restClient(){
//        return new RestTemplate();
//   }



   @Bean
    public WebClient webClient(){
        return WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader("Accept-Language", "EN")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
            .build();
   }

}
