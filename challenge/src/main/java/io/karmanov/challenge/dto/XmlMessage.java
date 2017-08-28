package io.karmanov.challenge.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "msg")
public class XmlMessage implements Message {

    @JacksonXmlProperty(localName = "id")
    private XmlMessageId id;

    @JacksonXmlProperty(localName = "done")
    private XmlMessageDoneStatus doneStatus;

    @Override
    public String getHash() {
        return id.getValue();
    }
}
