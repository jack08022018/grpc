package com.example.receiverservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "user")
@JsonPropertyOrder({ "id", "name" })
public class UserModel {
    @JacksonXmlProperty(isAttribute = true)
    private int id;

    @JacksonXmlProperty(localName = "NAME_XML")
    private String name;

    @JacksonXmlElementWrapper(localName = "cards")
    @JacksonXmlProperty(localName = "card")
    private List<String> cards;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "title")
    private List<String> titles;
}
