package com.sks.cbs.config;

import com.sks.cbs.model.CustomDutyProperties;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ManualXmlParser {

    public List<CustomDutyProperties> parseCustomsDuties(String xml) {
        List<CustomDutyProperties> duties = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));

            NodeList entries = document.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");

            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);

                NodeList contentNodes = entry.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "content");
                if (contentNodes.getLength() > 0) {
                    Element content = (Element) contentNodes.item(0);

                    NodeList propertiesNodes = content.getElementsByTagNameNS(
                            "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata", "properties");

                    if (propertiesNodes.getLength() > 0) {
                        Element properties = (Element) propertiesNodes.item(0);
                        CustomDutyProperties duty = parseProperties(properties);
                        duties.add(duty);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing XML manually: " + e.getMessage());
            e.printStackTrace();
        }

        return duties;
    }

    private CustomDutyProperties parseProperties(Element properties) {
        CustomDutyProperties duty = new CustomDutyProperties();

        duty.setTariffItemNumber(getElementValue(properties, "TariffItemNumber"));
        duty.setTariffTreatmentCode(getElementValue(properties, "TariffTreatmentCode"));
        duty.setGstCode(getElementValue(properties, "GSTCode"));
        duty.setAsOfDate(getElementValue(properties, "AsOfDate"));
        duty.setCustomsDutyValidStartDate(getElementValue(properties, "CustomsDutyValidStartDate"));
        duty.setCustomsDutyValidEndDate(getElementValue(properties, "CustomsDutyValidEndDate"));
        duty.setFreeQualifierIndicator(getElementValue(properties, "FreeQualifierIndicator"));

        // Parse numeric values
        duty.setSpecificRateRegValue(parseDouble(getElementValue(properties, "SpecificRateRegValue")));
        duty.setAdValoremRateMinValue(parseDouble(getElementValue(properties, "AdValoremRateMinValue")));
        duty.setAdValoremRateMaxValue(parseDouble(getElementValue(properties, "AdValoremRateMaxValue")));
        duty.setAdValoremRateRegValue(parseDouble(getElementValue(properties, "AdValoremRateRegValue")));
        duty.setSpecificRateMinValue(parseDouble(getElementValue(properties, "SpecificRateMinValue")));
        duty.setSpecificRateMaxValue(parseDouble(getElementValue(properties, "SpecificRateMaxValue")));

        // Parse qualifier codes
        duty.setSpecificRateRegQualifierCode(getElementValue(properties, "SpecificRateRegQualifierCode"));
        duty.setAdValoremRateMinQualifierCode(getElementValue(properties, "AdValoremRateMinQualifierCode"));
        duty.setAdValoremRateMaxQualifierCode(getElementValue(properties, "AdValoremRateMaxQualifierCode"));
        duty.setAdValoremRateRegQualifierCode(getElementValue(properties, "AdValoremRateRegQualifierCode"));
        duty.setSpecificRateMinQualifierCode(getElementValue(properties, "SpecificRateMinQualifierCode"));
        duty.setSpecificRateMaxQualifierCode(getElementValue(properties, "SpecificRateMaxQualifierCode"));

        // Parse boolean
        duty.setInactiveIndicator(parseBoolean(getElementValue(properties, "InactiveIndicator")));

        duty.setUpdateOn(getElementValue(properties, "UpdateOn"));
        duty.setUnitOfMeasureCode(getElementValue(properties, "UnitOfMeasureCode"));

        return duty;
    }

    private String getElementValue(Element parent, String elementName) {
        NodeList nodes = parent.getElementsByTagNameNS(
                "http://schemas.microsoft.com/ado/2007/08/dataservices", elementName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }
}