package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;

import java.util.List;

@Getter
@Setter
@Xml(childName = "entry")
@BeanConfig(ignoreUnknownBeanProperties = "true")
public class ODataEntry {

    @Xml(childName = "id")
    private String id;

    @Xml(childName = "title")
    private String title;

    @Xml(childName = "updated")
    private String updated;

    @Xml(childName = "category")
    private Category category;

    @Xml(childName = "link")
    private List<ODataLink> link;

    @Xml(childName = "content")
    private ODataContent content;
}