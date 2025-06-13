package com.sks.cbs.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.juneau.xml.annotation.Xml;
import org.apache.juneau.xml.annotation.XmlFormat;


@Getter
@Setter
//@Xml( prefix = "atom", namespace = Xmls.ATOM)
@Data

public class ODataTitle {
// Maps the text content of the <title> element
    @Xml(format = XmlFormat.ATTR)
    private String type;

    @Xml
    private String value;

    // Maps XML attributes of the <title> element

}
