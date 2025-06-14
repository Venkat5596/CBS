package com.sks.cbs.service;

import com.sks.cbs.config.ODataParser;
import com.sks.cbs.model.CustomDutyProperties;
import com.sks.cbs.repository.CustomDutyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomDutyService {

    private final CustomDutyRepository customDutyRepository;
    private final WebClient webClient;
    private final ODataParser oDataParser;

    @Transactional
    public Mono<List<CustomDutyProperties>> fetchAndSaveCustomsDuties() {
        return webClient.get()
                .uri("/gstCodes")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(xml -> log.info("Received XML response of length: {}", xml.length()))
                .map(oDataParser::parseCustomsDuties)
                .doOnNext(duties -> log.info("Parsed {} custom duty properties", duties.size()))
                .map(this::saveCustomDuties)
                .doOnNext(savedDuties -> log.info("Saved {} custom duty properties to database", savedDuties.size()))
                .doOnError(error -> log.error("Error processing custom duties: ", error));
    }

    @Transactional
    public List<CustomDutyProperties> saveCustomDuties(List<CustomDutyProperties> duties) {
        if (duties == null || duties.isEmpty()) {
            log.warn("No custom duties to save");
            return List.of();
        }

        try {
            List<CustomDutyProperties> savedDuties = customDutyRepository.saveAll(duties);
            log.info("Successfully saved {} custom duty properties", savedDuties.size());
            return savedDuties;
        } catch (Exception e) {
            log.error("Error saving custom duties to database: ", e);
            throw new RuntimeException("Failed to save custom duties", e);
        }
    }

    @Transactional
    public CustomDutyProperties saveOrUpdateCustomDuty(CustomDutyProperties duty) {
        if (duty.getTariffItemNumber() != null && duty.getTariffTreatmentCode() != null) {
            Optional<CustomDutyProperties> existing = customDutyRepository
                    .findByTariffItemNumberAndTariffTreatmentCode(
                            duty.getTariffItemNumber(),
                            duty.getTariffTreatmentCode());

            if (existing.isPresent()) {
                CustomDutyProperties existingDuty = existing.get();
                updateExistingDuty(existingDuty, duty);
                return customDutyRepository.save(existingDuty);
            }
        }
        return customDutyRepository.save(duty);
    }

    private void updateExistingDuty(CustomDutyProperties existing, CustomDutyProperties newDuty) {
        // Update all fields from new duty to existing
        existing.setGstCode(newDuty.getGstCode());
        existing.setAsOfDate(newDuty.getAsOfDate());
        existing.setCustomsDutyValidStartDate(newDuty.getCustomsDutyValidStartDate());
        existing.setCustomsDutyValidEndDate(newDuty.getCustomsDutyValidEndDate());
        existing.setFreeQualifierIndicator(newDuty.getFreeQualifierIndicator());
        existing.setSpecificRateRegValue(newDuty.getSpecificRateRegValue());
        existing.setSpecificRateRegQualifierCode(newDuty.getSpecificRateRegQualifierCode());
        existing.setAdValoremRateMinValue(newDuty.getAdValoremRateMinValue());
        existing.setAdValoremRateMinQualifierCode(newDuty.getAdValoremRateMinQualifierCode());
        existing.setAdValoremRateMaxValue(newDuty.getAdValoremRateMaxValue());
        existing.setAdValoremRateMaxQualifierCode(newDuty.getAdValoremRateMaxQualifierCode());
        existing.setAdValoremRateRegValue(newDuty.getAdValoremRateRegValue());
        existing.setAdValoremRateRegQualifierCode(newDuty.getAdValoremRateRegQualifierCode());
        existing.setSpecificRateMinValue(newDuty.getSpecificRateMinValue());
        existing.setSpecificRateMinQualifierCode(newDuty.getSpecificRateMinQualifierCode());
        existing.setSpecificRateMaxValue(newDuty.getSpecificRateMaxValue());
        existing.setSpecificRateMaxQualifierCode(newDuty.getSpecificRateMaxQualifierCode());
        existing.setInactiveIndicator(newDuty.getInactiveIndicator());
        existing.setUpdateOn(newDuty.getUpdateOn());
        existing.setUnitOfMeasureCode(newDuty.getUnitOfMeasureCode());
    }

    @Transactional(readOnly = true)
    public List<CustomDutyProperties> getAllCustomDuties() {
        return customDutyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CustomDutyProperties> getActiveCustomDuties() {
        return customDutyRepository.findAllActive();
    }

    @Transactional(readOnly = true)
    public Optional<CustomDutyProperties> findByTariffItemAndTreatment(String tariffItem, String treatmentCode) {
        return customDutyRepository.findByTariffItemNumberAndTariffTreatmentCode(tariffItem, treatmentCode);
    }

    @Transactional(readOnly = true)
    public List<CustomDutyProperties> findByTariffItem(String tariffItem) {
        return customDutyRepository.findByTariffItemNumber(tariffItem);
    }

    @Transactional(readOnly = true)
    public List<String> getAllDistinctTariffItems() {
        return customDutyRepository.findAllDistinctTariffItemNumbers();
    }

    @Transactional
    public void deleteAll() {
        customDutyRepository.deleteAll();
        log.info("Deleted all custom duty properties from database");
    }

    @Transactional(readOnly = true)
    public long getCount() {
        return customDutyRepository.count();
    }
}