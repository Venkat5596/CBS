package com.sks.cbs.config;

import com.sks.cbs.model.CustomDutyProperties;
import com.sks.cbs.model.ODataFeed;
import org.apache.juneau.xml.XmlParser;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ODataParser {

    private final XmlParser xmlParser;

    public ODataParser() {
        this.xmlParser = XmlParser
                .create()
                .ignoreUnknownBeanProperties()
                .trimStrings()
                .ignoreUnknownEnumValues()
                .build();
    }

    public List<CustomDutyProperties> parseCustomsDuties(String xml) {
        try {
            System.out.println("Parsing XML with Juneau: " + xml.substring(0, Math.min(500, xml.length())) + "...");

            // Try Juneau parser first
            try {
                ODataFeed feed = xmlParser.parse(xml, ODataFeed.class);

                if (feed != null && feed.getEntry() != null && !feed.getEntry().isEmpty()) {
                    System.out.println("Juneau parser successful. Entries count: " + feed.getEntry().size());

                    return feed.getEntry().stream()
                            .filter(entry -> entry != null &&
                                    entry.getContent() != null &&
                                    entry.getContent().getProperties() != null)
                            .map(entry -> entry.getContent().getProperties())
                            .collect(Collectors.toList());
                }
            } catch (Exception juneauException) {
                System.out.println("Juneau parser failed: " + juneauException.getMessage());
            }

            // Fallback to manual DOM parsing
            System.out.println("Falling back to manual DOM parsing...");
            return parseWithDOM(xml);

        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    private List<CustomDutyProperties> parseWithDOM(String xml) {
        List<CustomDutyProperties> duties = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));

            // Look for entry elements in the Atom namespace
            NodeList entries = document.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");
            System.out.println("Found " + entries.getLength() + " entries");

            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);

                // Get content element
                NodeList contentNodes = entry.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "content");
                if (contentNodes.getLength() > 0) {
                    Element content = (Element) contentNodes.item(0);

                    // Get properties element
                    NodeList propertiesNodes = content.getElementsByTagNameNS(
                            "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata", "properties");

                    if (propertiesNodes.getLength() > 0) {
                        Element properties = (Element) propertiesNodes.item(0);
                        CustomDutyProperties duty = parsePropertiesElement(properties);
                        if (duty != null) {
                            duties.add(duty);
                        }
                    }
                }
            }

            System.out.println("Successfully parsed " + duties.size() + " custom duty properties");

        } catch (Exception e) {
            System.err.println("Error in DOM parsing: " + e.getMessage());
            e.printStackTrace();
        }

        return duties;
    }

    private CustomDutyProperties parsePropertiesElement(Element properties) {
        try {
            CustomDutyProperties duty = new CustomDutyProperties();

            duty.setTariffItemNumber(getElementValue(properties, "TariffItemNumber"));
            duty.setTariffTreatmentCode(getElementValue(properties, "TariffTreatmentCode"));
            duty.setGstCode(getElementValue(properties, "GSTCode"));
            duty.setAsOfDate(getElementValue(properties, "AsOfDate"));
            duty.setCustomsDutyValidStartDate(getElementValue(properties, "CustomsDutyValidStartDate"));
            duty.setCustomsDutyValidEndDate(getElementValue(properties, "CustomsDutyValidEndDate"));
            duty.setFreeQualifierIndicator(getElementValue(properties, "FreeQualifierIndicator"));

            // Parse numeric values with error handling
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
        } catch (Exception e) {
            System.err.println("Error parsing properties element: " + e.getMessage());
            return null;
        }
    }

    private String getElementValue(Element parent, String elementName) {
        NodeList nodes = parent.getElementsByTagNameNS(
                "http://schemas.microsoft.com/ado/2007/08/dataservices", elementName);
        if (nodes.getLength() > 0) {
            String value = nodes.item(0).getTextContent();
            return (value != null && !value.trim().isEmpty()) ? value.trim() : null;
        }
        return null;
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing double value: " + value);
            return 0.0;
        }
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return Boolean.parseBoolean(value.trim());
    }
}