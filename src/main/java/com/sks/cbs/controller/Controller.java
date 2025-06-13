package com.sks.cbs.controller;

import com.sks.cbs.config.ODataParser;
//import com.sks.cbs.config.Parser;
import com.sks.cbs.model.CustomDutyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class Controller {
    private final WebClient webClient;
    private final ODataParser parser;


    @GetMapping("/get")
    public Mono<List<CustomDutyProperties>> getGSTCodesMono() {
        return webClient.get()
                .uri("/gstCodes")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(System.out::println)
                .map(xml -> {
                    try {
                        return parser.parseCustomsDuties(xml);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });}




@GetMapping("/getstring")
public Mono<String> getGSTCodesMono1() {
    return  webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/gstCodes")
                    .build())
            .retrieve()
            .bodyToMono(String.class);
//            .map(xml -> {
//                try {
//                    return new Paser().parse(xml);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

}

}



//    @GetMapping("/getGSTCodes")
//    public ResponseEntity<String> getGSTCodes(){
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(List.of(MediaType.APPLICATION_XML));
//        System.out.println(headers);
//        headers.set("Accept-Language", "EN");
//        System.out.println(headers);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        System.out.println(entity);
//
//       // return restTemplate.getForObject(WebClientConfig.BASE_URL, String.class);
//
//        return restTemplate
//                .exchange(WebClientConfig.BASE_URL,
//                        HttpMethod.GET,
//                        entity,
//                        String.class);
//    }
//
//    public WebClient getWebClient() {
//        return webClient;
//    }
//}
