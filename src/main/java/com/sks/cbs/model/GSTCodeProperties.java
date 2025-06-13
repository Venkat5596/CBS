package com.sks.cbs.model;


import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.xml.annotation.Xml;


@Xml(childName = "properties",prefix = "m", namespace = Xmls.ODATA_METADATA)
@Getter
@Setter
public class GSTCodeProperties {
    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String gstCode;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String asOfDate;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String language;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String gstCodeValidStartDate;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String gstCodeValidEndDate;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Boolean exciseTaxRateCheckIndicator;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Integer gstCheckGroup;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String gstRateType;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private Boolean inactiveIndicator;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String description;

    @Xml(prefix = "d", namespace = Xmls.ODATA_DATASERVICES)
    private String updateOn;


}
