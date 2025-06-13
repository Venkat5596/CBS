package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Xml(childName = "author" , namespace = Xmls.ATOM)
@Getter
@Setter
@BeanConfig(ignoreUnknownBeanProperties = "true")
public class ODataAuthor {
    @Xml(childName = "name" , namespace = Xmls.ATOM)
    private String name;
}
