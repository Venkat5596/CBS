package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.xml.annotation.Xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "link" , namespace = Xmls.ATOM)
@Xml
@Getter
@Setter
public class ODataLink {

    @XmlAttribute(name = "href")
    private String href;
@XmlAttribute(name = "rel")
    private String rel;
@XmlAttribute(name = "title")
    private String title;
}
