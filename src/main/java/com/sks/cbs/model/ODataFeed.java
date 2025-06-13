package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;

import java.util.List;

@Xml(childName = "feed", prefix = "atom", namespace = Xmls.ATOM)
@Getter
@Setter
@BeanConfig(ignoreUnknownBeanProperties = "true")
public class ODataFeed {

    @Xml(childName = "id")
    private String id;

    @Xml(childName = "title")
    private String title;

    @Xml(childName = "updated")
    private String updated;

    @Xml(childName = "author")
    private ODataAuthor author;

    @Xml(childName = "link")
    private List<ODataLink> link;

    @Xml(childName = "entry")
    private List<ODataEntry> entry;
}