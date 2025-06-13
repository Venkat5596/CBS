package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.Bean;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;

import java.util.List;

@Xml(childName =  "feed" ,prefix = "atom", namespace = Xmls.ATOM )
@Getter
@Setter
@BeanConfig(ignoreUnknownBeanProperties = "true")
public class ODataFeed {
    @Xml(childName = "id" , namespace = Xmls.ATOM)
    private String id;

    @Xml(childName ="title")
    private String title;

    @Xml(childName = "updated" , namespace = Xmls.ATOM)
    private String updated;

    @Xml(childName = "author" , namespace = Xmls.ATOM)
    private ODataAuthor author;

    @Xml(childName = "link")
    private List<ODataLink> link;

    @Xml(childName = "entry" , namespace = Xmls.ATOM)
    private List<ODataEntry> entry;

     // List because there can be multiple <link> elements
}
