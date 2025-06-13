package com.sks.cbs.config;

import com.sks.cbs.model.CustomDutyProperties;
import com.sks.cbs.model.ODataFeed;
//import lombok.RequiredArgsConstructor;
//import org.apache.juneau.BeanContext;
import org.apache.juneau.xml.XmlParser;
import org.springframework.stereotype.Component;

import java.util.List;
//import java.util.Map;

@Component
public class ODataParser {

    private final XmlParser xmlParser;

    public ODataParser() {
        XmlParser.Builder builder = XmlParser
                .create()
                .ignoreUnknownBeanProperties()
                .trimStrings();

        this.xmlParser = builder.build();
    }

    public List<CustomDutyProperties> parseCustomsDuties(String xml){
        ODataFeed feed = xmlParser.parse(xml,ODataFeed.class);

        if(feed ==null  || feed.getEntry()==null){
            System.out.println("Feed is null");
            return List.of();

        }



        System.out.println("size : "+feed.getEntry().size());

        System.out.println("id: "+feed.getId().toString() );
        System.out.println(" title: " +feed.getTitle().toString());
//        System.out.println("Update : "+ feed.getUpdated());
//        System.out.println("author : "+feed.getAuthor().getName());
//        System.out.println("Link : ");
//        System.out.println("Link : "+feed.getLink().get(0));
//        System.out.println(feed.getEntry().get(0).getTitle());
//        System.out.println(feed.getEntry().get(0).getUpdated());
//        System.out.println(feed.getEntry().get(0).getCategory().getTerm());
//        System.out.println(feed.getEntry().get(0).getLink().get(0).getHref());


        return feed.getEntry().stream()
                .map(entry -> entry.getContent().getProperties())
                .toList();
    }


}