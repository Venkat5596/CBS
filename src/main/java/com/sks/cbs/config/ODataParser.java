package com.sks.cbs.config;

import com.sks.cbs.model.CustomDutyProperties;
import com.sks.cbs.model.ODataFeed;
import org.apache.juneau.xml.XmlParser;
import org.springframework.stereotype.Component;

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
                .build();
    }

    public List<CustomDutyProperties> parseCustomsDuties(String xml) {
        try {
            System.out.println("Parsing XML: " + xml.substring(0, Math.min(500, xml.length())) + "...");

            ODataFeed feed = xmlParser.parse(xml, ODataFeed.class);

            if (feed == null) {
                System.out.println("Feed is null");
                return List.of();
            }

            if (feed.getEntry() == null || feed.getEntry().isEmpty()) {
                System.out.println("No entries found in feed");
                return List.of();
            }

            System.out.println("Feed parsed successfully. Entries count: " + feed.getEntry().size());
            System.out.println("Feed ID: " + feed.getId());
            System.out.println("Feed Title: " + feed.getTitle());

            return feed.getEntry().stream()
                    .filter(entry -> {
                        if (entry == null) {
                            System.out.println("Null entry found");
                            return false;
                        }
                        if (entry.getContent() == null) {
                            System.out.println("Entry content is null for entry: " + entry.getId());
                            return false;
                        }
                        if (entry.getContent().getProperties() == null) {
                            System.out.println("Entry properties is null for entry: " + entry.getId());
                            return false;
                        }
                        return true;
                    })
                    .map(entry -> entry.getContent().getProperties())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}