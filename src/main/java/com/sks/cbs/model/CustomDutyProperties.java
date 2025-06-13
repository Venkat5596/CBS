package com.sks.cbs.model;


import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;



@Xml(
        prefix="d",
        namespace=Xmls.ODATA_DATASERVICES)
@Getter
@Setter
@BeanConfig(ignoreUnknownBeanProperties = "true")
public class CustomDutyProperties  {


    private String TariffItemNumber;
    private String TariffTreatmentCode;
    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String GSTCode;
    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String AsOfDate;
    private String CustomsDutyValidStartDate;
    private String CustomsDutyValidEndDate;
    private String FreeQualifierIndicator;
    private Double SpecificRateRegValue;
    private String SpecificRateRegQualifierCode;
    private Double AdValoremRateMinValue;
    private String AdValoremRateMinQualifierCode;
    private Double AdValoremRateMaxValue;
    private String AdValoremRateMaxQualifierCode;
    private Double AdValoremRateRegValue;
    private String AdValoremRateRegQualifierCode;
    private Double SpecificRateMinValue;
    private String SpecificRateMinQualifierCode;
    private Double SpecificRateMaxValue;
    private String SpecificRateMaxQualifierCode;
    private Boolean InactiveIndicator;
    private String UpdateOn;
    private String UnitOfMeasureCode;
//    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
//    private String TariffItemNumber; // Matches <d:TariffItemNumber>
//
//    @Xml(prefix = "d",namespace = Xmls.ODATA_DATASERVICES)
//    private String TariffTreatmentCode;
//
//
//    @Xml(childName = "d:GSTCode")
//    private String gstCode;// Matches <d:TariffTreatmentCode>
//
//    @Xml(prefix = "d",namespace = Xmls.ODATA_DATASERVICES)
//    private String AsOfDate; // Matches <d:AsOfDate>. Consider java.time.LocalDateTime for parsing later.
//
//    @Xml(prefix = "d",namespace = Xmls.ODATA_DATASERVICES)
//    private String CustomsDutyValidStartDate; // Matches <d:CustomsDutyValidStartDate>
//
//    @Xml(prefix = "d",namespace = Xmls.ODATA_DATASERVICES)
//    private String CustomsDutyValidEndDate; // Matches <d:CustomsDutyValidEndDate>
//
//    @Xml(prefix = "d",namespace = Xmls.ODATA_DATASERVICES)
//    private String FreeQualifierIndicator; // Matches <d:FreeQualifierIndicator> ('X' will be a String)
//
//    @Xml(prefix = "d",namespace = Xmls.ODATA_DATASERVICES)
//    private Double SpecificRateRegValue; // Matches <d:SpecificRateRegValue>
//
//    @Xml(prefix = "d",namespace = Xmls.ODATA_DATASERVICES)
//    private String SpecificRateRegQualifierCode; // Matches <d:SpecificRateRegQualifierCode>
//
//    @Xml(prefix = "d",namespace = Xmls.ODATA_DATASERVICES)
//    private Double AdValoremRateMinValue; // Matches <d:AdValoremRateMinValue>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private String AdValoremRateMinQualifierCode; // Matches <d:AdValoremRateMinQualifierCode>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private Double AdValoremRateMaxValue; // Matches <d:AdValoremRateMaxValue>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private String AdValoremRateMaxQualifierCode; // Matches <d:AdValoremRateMaxQualifierCode>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private Double AdValoremRateRegValue; // Matches <d:AdValoremRateRegValue>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private String AdValoremRateRegQualifierCode; // Matches <d:AdValoremRateRegQualifierCode>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private Double SpecificRateMinValue; // Matches <d:SpecificRateMinValue>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private String SpecificRateMinQualifierCode; // Matches <d:SpecificRateMinQualifierCode>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private Double SpecificRateMaxValue; // Matches <d:SpecificRateMaxValue>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private String SpecificRateMaxQualifierCode; // Matches <d:SpecificRateMaxQualifierCode>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private Boolean InactiveIndicator; // Matches <d:InactiveIndicator> ('false' will be a Boolean)
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private String UpdateOn; // Matches <d:UpdateOn>
//
//    @Xml(namespace = Xmls.ODATA_DATASERVICES)
//    private String UnitOfMeasureCode; // Matches <d:UnitOfMeasureCode>
}