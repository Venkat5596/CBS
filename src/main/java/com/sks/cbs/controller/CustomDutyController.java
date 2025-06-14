package com.sks.cbs.controller;

import com.sks.cbs.config.ManualXmlParser;
import com.sks.cbs.config.ODataParser;
import com.sks.cbs.model.CustomDutyProperties;
import com.sks.cbs.service.CustomDutyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/custom-duties")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CustomDutyController {

    private final WebClient webClient;
    private final ODataParser parser;
    private final ManualXmlParser manualParser;
    private final CustomDutyService customDutyService;

    /**
     * Fetch custom duties from external API and return parsed data
     */
    @GetMapping("/fetch")
    public Mono<ResponseEntity<List<CustomDutyProperties>>> fetchCustomDuties() {
        log.info("Fetching custom duties from external API");

        return webClient.get()
                .uri("/gstCodes")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(xml -> log.info("Received XML response of length: {}", xml.length()))
                .map(xml -> {
                    try {
                        // Try OData parser first
                        List<CustomDutyProperties> result = parser.parseCustomsDuties(xml);
                        if (result.isEmpty()) {
                            log.info("OData parser returned empty result, trying manual parser...");
                            result = manualParser.parseCustomsDuties(xml);
                        }
                        return ResponseEntity.ok(result);
                    } catch (Exception e) {
                        log.error("Error with OData parser, trying manual parser: ", e);
                        try {
                            List<CustomDutyProperties> result = manualParser.parseCustomsDuties(xml);
                            return ResponseEntity.ok(result);
                        } catch (Exception e2) {
                            log.error("Manual parser also failed: ", e2);
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<List<CustomDutyProperties>>body(null);
                        }
                    }
                })
                .doOnError(error -> log.error("Error fetching custom duties: ", error));
    }

    /**
     * Fetch and save custom duties to database
     */
    @PostMapping("/fetch-and-save")
    public Mono<ResponseEntity<Map<String, Object>>> fetchAndSaveCustomDuties() {
        log.info("Fetching and saving custom duties to database");

        return customDutyService.fetchAndSaveCustomsDuties()
                .map(savedDuties -> {
                    Map<String, Object> response = Map.of(
                            "message", "Custom duties fetched and saved successfully",
                            "count", savedDuties.size(),
                            "status", "success"
                    );
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(error -> {
                    log.error("Error fetching and saving custom duties: ", error);
                    Map<String, Object> errorResponse = Map.of(
                            "message", "Error fetching and saving custom duties",
                            "error", error.getMessage(),
                            "status", "error"
                    );
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                });
    }

    /**
     * Get raw XML response from external API
     */
    @GetMapping("/raw-xml") // output https://IPORTAL-IPORTAIL.CBSA-ASFC.CA:443/sap/opu/odata/sap/TARIFF/gstCodes2025-06-14T03:14:41Zhttps://IPORTAL-IPORTAIL.CBSA-ASFC.CA:443/sap/opu/odata/sap/TARIFF/gstCodes(GSTCode='01',AsOfDate=datetime'2025-06-13T00%3A00%3A00')2025-06-14T03:14:41Z012025-06-13T00:00:00EN1991-01-01T00:00:009999-12-31T00:00:00true1VfalseNORMAL RATE2021-05-25T00:00:00https://IPORTAL-IPORTAIL.CBSA-ASFC.CA:443/sap/opu/odata/sap/TARIFF/gstCodes(GSTCode='48',AsOfDate=datetime'2025-06-13T00%3A00%3A00')2025-06-14T03:14:41Z482025-06-13T00:00:00EN2024-10-21T00:00:009999-12-31T00:00:00true2EfalsePublications supplied by non-resident registrant -regulations2024-08-16T00:00:00https://IPORTAL-IPORTAIL.CBSA-ASFC.CA:443/sap/opu/odata/sap/TARIFF/gstCodes(GSTCode='49',AsOfDate=datetime'2025-06-13T00%3A00%3A00')2025-06-14T03:14:41Z492025-06-13T00:00:00EN2024-10-21T00:00:009999-12-31T00:00:00true2EfalseStocks, bond certifications, money2024-08-13T00:00:00https://IPORTAL-IPORTAIL.CBSA-ASFC.CA:443/sap/opu/odata/sap/TARIFF/gstCodes(GSTCode='51',AsOfDate=datetime'2025-06-13T00%3A00%3A00')2025-06-14T03:14:41Z512025-06-13T00:00:00EN2024-10-
    public Mono<ResponseEntity<String>> getRawXml() {
        log.info("Fetching raw XML from external API");

        return webClient.get()
                .uri("/gstCodes")
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok)
                .doOnError(error -> log.error("Error fetching raw XML: ", error));
    }

    /**
     * Get all custom duties from database
     */
    @GetMapping
    public ResponseEntity<List<CustomDutyProperties>> getAllCustomDuties() {
        log.info("Fetching all custom duties from database");

        try {
            List<CustomDutyProperties> duties = customDutyService.getAllCustomDuties();
            return ResponseEntity.ok(duties);
        } catch (Exception e) {
            log.error("Error fetching all custom duties: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get active custom duties only
     */
    @GetMapping("/active")
    public ResponseEntity<List<CustomDutyProperties>> getActiveCustomDuties() {
        log.info("Fetching active custom duties from database");

        try {
            List<CustomDutyProperties> duties = customDutyService.getActiveCustomDuties();
            return ResponseEntity.ok(duties);
        } catch (Exception e) {
            log.error("Error fetching active custom duties: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get custom duty by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomDutyProperties> getCustomDutyById(@PathVariable Long id) {
        log.info("Fetching custom duty with ID: {}", id);

        // Note: You might need to add findById method to your service
        return ResponseEntity.notFound().build(); // Placeholder - implement in service
    }

    /**
     * Search by tariff item number
     */
    @GetMapping("/tariff-item/{tariffItemNumber}")
    public ResponseEntity<List<CustomDutyProperties>> getByTariffItem(@PathVariable String tariffItemNumber) {
        log.info("Searching custom duties by tariff item number: {}", tariffItemNumber);

        try {
            List<CustomDutyProperties> duties = customDutyService.findByTariffItem(tariffItemNumber);
            return ResponseEntity.ok(duties);
        } catch (Exception e) {
            log.error("Error searching by tariff item: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Search by tariff item and treatment code
     */
    @GetMapping("/search")
    public ResponseEntity<CustomDutyProperties> searchByTariffAndTreatment(
            @RequestParam String tariffItem,
            @RequestParam String treatmentCode) {

        log.info("Searching custom duty by tariff item: {} and treatment code: {}", tariffItem, treatmentCode);

        try {
            Optional<CustomDutyProperties> duty = customDutyService.findByTariffItemAndTreatment(tariffItem, treatmentCode);
            return duty.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error searching by tariff and treatment: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all distinct tariff item numbers
     */
    @GetMapping("/tariff-items")
    public ResponseEntity<List<String>> getAllTariffItems() {
        log.info("Fetching all distinct tariff item numbers");

        try {
            List<String> tariffItems = customDutyService.getAllDistinctTariffItems();
            return ResponseEntity.ok(tariffItems);
        } catch (Exception e) {
            log.error("Error fetching tariff items: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Create new custom duty
     */
    @PostMapping
    public ResponseEntity<CustomDutyProperties> createCustomDuty(@RequestBody CustomDutyProperties customDuty) {
        log.info("Creating new custom duty for tariff item: {}", customDuty.getTariffItemNumber());

        try {
            CustomDutyProperties savedDuty = customDutyService.saveOrUpdateCustomDuty(customDuty);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDuty);
        } catch (Exception e) {
            log.error("Error creating custom duty: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update existing custom duty
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomDutyProperties> updateCustomDuty(
            @PathVariable Long id,
            @RequestBody CustomDutyProperties customDuty) {

        log.info("Updating custom duty with ID: {}", id);

        try {
            customDuty.setId(id);
            CustomDutyProperties updatedDuty = customDutyService.saveOrUpdateCustomDuty(customDuty);
            return ResponseEntity.ok(updatedDuty);
        } catch (Exception e) {
            log.error("Error updating custom duty: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete all custom duties
     */
    @DeleteMapping("/all")
    public ResponseEntity<Map<String, String>> deleteAllCustomDuties() {
        log.info("Deleting all custom duties");

        try {
            customDutyService.deleteAll();
            Map<String, String> response = Map.of(
                    "message", "All custom duties deleted successfully",
                    "status", "success"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error deleting all custom duties: ", e);
            Map<String, String> errorResponse = Map.of(
                    "message", "Error deleting custom duties",
                    "error", e.getMessage(),
                    "status", "error"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get database statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        log.info("Fetching database statistics");

        try {
            long totalCount = customDutyService.getCount();
            List<CustomDutyProperties> activeDuties = customDutyService.getActiveCustomDuties();
            long activeCount = activeDuties.size();
            List<String> tariffItems = customDutyService.getAllDistinctTariffItems();

            Map<String, Object> stats = Map.of(
                    "totalRecords", totalCount,
                    "activeRecords", activeCount,
                    "inactiveRecords", totalCount - activeCount,
                    "distinctTariffItems", tariffItems.size(),
                    "status", "success"
            );

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("Error fetching database stats: ", e);
            Map<String, Object> errorResponse = Map.of(
                    "message", "Error fetching statistics",
                    "error", e.getMessage(),
                    "status", "error"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> health = Map.of(
                "status", "UP",
                "service", "Custom Duty Controller",
                "timestamp", java.time.LocalDateTime.now().toString()
        );
        return ResponseEntity.ok(health);
    }

    /**
     * Sync data - fetch from API and update database
     */
    @PostMapping("/sync")
    public Mono<ResponseEntity<Map<String, Object>>> syncData() {
        log.info("Syncing data from external API to database");

        return customDutyService.fetchAndSaveCustomsDuties()
                .map(savedDuties -> {
                    Map<String, Object> response = Map.of(
                            "message", "Data synchronized successfully",
                            "recordsProcessed", savedDuties.size(),
                            "timestamp", java.time.LocalDateTime.now().toString(),
                            "status", "success"
                    );
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(error -> {
                    log.error("Error during data sync: ", error);
                    Map<String, Object> errorResponse = Map.of(
                            "message", "Error during data synchronization",
                            "error", error.getMessage(),
                            "timestamp", java.time.LocalDateTime.now().toString(),
                            "status", "error"
                    );
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                });
    }
}