package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;

@Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
@Getter
@Setter
@BeanConfig(ignoreUnknownBeanProperties = "true")
public class CustomDutyProperties {

    @Xml(childName = "TariffItemNumber", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String tariffItemNumber;

    @Xml(childName = "TariffTreatmentCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String tariffTreatmentCode;

    @Xml(childName = "GSTCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String gstCode;

    @Xml(childName = "AsOfDate", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String asOfDate;

    @Xml(childName = "CustomsDutyValidStartDate", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String customsDutyValidStartDate;

    @Xml(childName = "CustomsDutyValidEndDate", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String customsDutyValidEndDate;

    @Xml(childName = "FreeQualifierIndicator", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String freeQualifierIndicator;

    @Xml(childName = "SpecificRateRegValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double specificRateRegValue;

    @Xml(childName = "SpecificRateRegQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String specificRateRegQualifierCode;

    @Xml(childName = "AdValoremRateMinValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double adValoremRateMinValue;

    @Xml(childName = "AdValoremRateMinQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String adValoremRateMinQualifierCode;

    @Xml(childName = "AdValoremRateMaxValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double adValoremRateMaxValue;

    @Xml(childName = "AdValoremRateMaxQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String adValoremRateMaxQualifierCode;

    @Xml(childName = "AdValoremRateRegValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double adValoremRateRegValue;

    @Xml(childName = "AdValoremRateRegQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String adValoremRateRegQualifierCode;

    @Xml(childName = "SpecificRateMinValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double specificRateMinValue;

    @Xml(childName = "SpecificRateMinQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String specificRateMinQualifierCode;

    @Xml(childName = "SpecificRateMaxValue", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Double specificRateMaxValue;

    @Xml(childName = "SpecificRateMaxQualifierCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String specificRateMaxQualifierCode;

    @Xml(childName = "InactiveIndicator", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Boolean inactiveIndicator;

    @Xml(childName = "UpdateOn", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String updateOn;

    @Xml(childName = "UnitOfMeasureCode", prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String unitOfMeasureCode;
}