package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.xml.annotation.Xml;
import org.apache.juneau.xml.annotation.XmlFormat;

@Getter
@Setter
@Xml(childName = "category")
public class Category {
    @Xml(childName = "term", format = XmlFormat.ATTR)
    private String term;

    @Xml(childName = "scheme", format = XmlFormat.ATTR)
    private String scheme;
}