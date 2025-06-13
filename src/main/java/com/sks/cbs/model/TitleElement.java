package com.sks.cbs.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;
import org.apache.juneau.xml.annotation.XmlFormat;

@Getter
@Setter
//@Xml( childName = "title",prefix = "atom", namespace=Xmls.ATOM)
//@BeanConfig(ignoreUnknownBeanProperties = "true")
public class TitleElement {
    @Xml(format = XmlFormat.ATTR)
    private String value;

    @Xml(format = XmlFormat.TEXT)
    private String type;
}
