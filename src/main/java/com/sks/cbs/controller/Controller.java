package com.sks.cbs.controller;

import com.sks.cbs.config.ManualXmlParser;
import com.sks.cbs.config.ODataParser;
import com.sks.cbs.model.CustomDutyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final WebClient webClient;
    private final ODataParser parser;
    private final ManualXmlParser manualParser;

    @GetMapping("/get")
    public Mono<List<CustomDutyProperties>> getGSTCodesMono() {
        return webClient.get()
                .uri("/gstCodes")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(xml -> System.out.println("Received XML: " + xml.substring(0, Math.min(500, xml.length()))))
                .map(xml -> {
                    try {
                        // Try Juneau parser first
                        List<CustomDutyProperties> result = parser.parseCustomsDuties(xml);
                        if (result.isEmpty()) {
                            System.out.println("Juneau parser returned empty result, trying manual parser...");
                            result = manualParser.parseCustomsDuties(xml);
                        }
                        return result;
                    } catch (Exception e) {
                        System.err.println("Error with Juneau parser, trying manual parser: " + e.getMessage());
                        try {
                            return manualParser.parseCustomsDuties(xml);
                        } catch (Exception e2) {
                            System.err.println("Manual parser also failed: " + e2.getMessage());
                            throw new RuntimeException("Both parsers failed", e2);
                        }
                    }
                });
    }

    @GetMapping("/get-manual")
    public Mono<List<CustomDutyProperties>> getGSTCodesManual() {
        return webClient.get()
                .uri("/gstCodes")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(xml -> System.out.println("Received XML: " + xml.substring(0, Math.min(500, xml.length()))))
                .map(manualParser::parseCustomsDuties);
    }

    @GetMapping("/getstring")
    public Mono<String> getGSTCodesMono1() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/gstCodes")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}