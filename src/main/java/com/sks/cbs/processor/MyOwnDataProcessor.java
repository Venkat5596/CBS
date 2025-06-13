//
//package com.sks.cbs.processor;
//
//import com.sks.cbs.config.Parser;
//import com.sks.cbs.model.*;
//import org.apache.juneau.BeanSession; // Keep this import
//import org.apache.juneau.xml.XmlParser; // Keep this import for its type if you are not using your Parser bean
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.Map;
//
//
//@Service
//public class MyODataProcessor {
//
//    private final Parser parser; // This is your configured Juneau XmlParser
//
//    @Autowired
//    public MyODataProcessor(Parser parser) {
//        this.parser = parser;
//    }
//
//    public void processODataFeed(String xmlString) throws Exception {
//        // Use the injected and configured parser (which is an XmlParser instance)
//        ODataFeed feed = parser.parse(xmlString, ODataFeed.class);
//
//        for (ODataEntry entry : feed.getEntry()) {
//            if (entry.getContent() != null && entry.getContent().getProperties() != null) {
//                Map<String, Object> rawPropertiesMap = entry.getContent().getProperties();
//
//                if (entry.getCategory() != null && entry.getCategory().getTerm() != null) {
//                    String entryCategoryTerm = entry.getCategory().getTerm();
//
//                    if (entryCategoryTerm.contains("customsDutiesType")) {
//                        // THIS IS THE FIX: Create BeanSession directly from the parser (which is a BeanContext)
//                        // In Juneau 7.x, BeanSession.create() takes the BeanContext.
//                        BeanSession beanSession = BeanSession.create(parser.getParserContext()).build();
//                        // OR, if 'parser' directly extends BeanContext and has the method (check your actual XmlParser.class)
//                        // BeanSession beanSession = parser.createBeanSession(); // Try this first if the above doesn't compile
//
//                        CustomDutyProperties customsProps = beanSession.convertToType(rawPropertiesMap, CustomDutyProperties.class);
//
//                        System.out.println("Parsed Tariff Item Number: " + customsProps.getTariffItemNumber());
//                        System.out.println("Parsed As Of Date: " + customsProps.getAsOfDate());
//                    }
//                }
//            }
//        }
//    }