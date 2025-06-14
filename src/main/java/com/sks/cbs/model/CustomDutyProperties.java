package com.sks.cbs.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;

import java.time.LocalDateTime;

@Entity
@Table(name = "custom_duty_properties")
@Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
@Getter
@Setter
@BeanConfig(ignoreUnknownBeanProperties = "true")
public class CustomDutyProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tariff_item_number")
    @Xml(childName = "TariffItemNumber", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String tariffItemNumber;

    @Column(name = "tariff_treatment_code")
    @Xml(childName = "TariffTreatmentCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String tariffTreatmentCode;

    @Column(name = "gst_code")
    @Xml(childName = "GSTCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String gstCode;

    @Column(name = "as_of_date")
    @Xml(childName = "AsOfDate", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String asOfDate;

    @Column(name = "customs_duty_valid_start_date")
    @Xml(childName = "CustomsDutyValidStartDate", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String customsDutyValidStartDate;

    @Column(name = "customs_duty_valid_end_date")
    @Xml(childName = "CustomsDutyValidEndDate", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String customsDutyValidEndDate;

    @Column(name = "free_qualifier_indicator")
    @Xml(childName = "FreeQualifierIndicator", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String freeQualifierIndicator;

    @Column(name = "specific_rate_reg_value")
    @Xml(childName = "SpecificRateRegValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double specificRateRegValue;

    @Column(name = "specific_rate_reg_qualifier_code")
    @Xml(childName = "SpecificRateRegQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String specificRateRegQualifierCode;

    @Column(name = "ad_valorem_rate_min_value")
    @Xml(childName = "AdValoremRateMinValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double adValoremRateMinValue;

    @Column(name = "ad_valorem_rate_min_qualifier_code")
    @Xml(childName = "AdValoremRateMinQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String adValoremRateMinQualifierCode;

    @Column(name = "ad_valorem_rate_max_value")
    @Xml(childName = "AdValoremRateMaxValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double adValoremRateMaxValue;

    @Column(name = "ad_valorem_rate_max_qualifier_code")
    @Xml(childName = "AdValoremRateMaxQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String adValoremRateMaxQualifierCode;

    @Column(name = "ad_valorem_rate_reg_value")
    @Xml(childName = "AdValoremRateRegValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double adValoremRateRegValue;

    @Column(name = "ad_valorem_rate_reg_qualifier_code")
    @Xml(childName = "AdValoremRateRegQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String adValoremRateRegQualifierCode;

    @Column(name = "specific_rate_min_value")
    @Xml(childName = "SpecificRateMinValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double specificRateMinValue;

    @Column(name = "specific_rate_min_qualifier_code")
    @Xml(childName = "SpecificRateMinQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String specificRateMinQualifierCode;

    @Column(name = "specific_rate_max_value")
    @Xml(childName = "SpecificRateMaxValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double specificRateMaxValue;

    @Column(name = "specific_rate_max_qualifier_code")
    @Xml(childName = "SpecificRateMaxQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String specificRateMaxQualifierCode;

    @Column(name = "inactive_indicator")
    @Xml(childName = "InactiveIndicator", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Boolean inactiveIndicator;

    @Column(name = "update_on")
    @Xml(childName = "UpdateOn", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String updateOn;

    @Column(name = "unit_of_measure_code")
    @Xml(childName = "UnitOfMeasureCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String unitOfMeasureCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}