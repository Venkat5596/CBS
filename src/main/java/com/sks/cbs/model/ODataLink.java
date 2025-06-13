package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.xml.annotation.Xml;
import org.apache.juneau.xml.annotation.XmlFormat;

@Xml(childName = "link")
@Getter
@Setter
public class ODataLink {

    @Xml(childName = "href", format = XmlFormat.ATTR)
    private String href;

    @Xml(childName = "rel", format = XmlFormat.ATTR)
    private String rel;

    @Xml(childName = "title", format = XmlFormat.ATTR)
    private String title;
}