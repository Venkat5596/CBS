package com.sks.cbs.repository;

import com.sks.cbs.model.CustomDutyProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomDutyRepository extends JpaRepository<CustomDutyProperties, Long> {

    Optional<CustomDutyProperties> findByTariffItemNumberAndTariffTreatmentCode(
            String tariffItemNumber, String tariffTreatmentCode);

    List<CustomDutyProperties> findByTariffItemNumber(String tariffItemNumber);

    List<CustomDutyProperties> findByTariffTreatmentCode(String tariffTreatmentCode);

    @Query("SELECT c FROM CustomDutyProperties c WHERE c.inactiveIndicator = false")
    List<CustomDutyProperties> findAllActive();

    @Query("SELECT DISTINCT c.tariffItemNumber FROM CustomDutyProperties c ORDER BY c.tariffItemNumber")
    List<String> findAllDistinctTariffItemNumbers();
}