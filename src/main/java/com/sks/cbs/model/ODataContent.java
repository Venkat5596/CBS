package com.sks.cbs.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.xml.annotation.Xml;
import org.apache.juneau.xml.annotation.XmlFormat;

@Getter
@Setter
@BeanConfig(ignoreUnknownBeanProperties = "true")
@Xml(childName = "content")
public class ODataContent {

    @Xml(format = XmlFormat.ATTR)
    private String type;

    @Xml(childName = "properties", prefix = "m", namespace = Xmls.ODATA_METADATA)
    private CustomDutyProperties properties;
}