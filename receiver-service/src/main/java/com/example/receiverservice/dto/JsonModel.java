package com.example.receiverservice.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

//@Getter
//@Setter
@Accessors(chain = true, fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonPropertyOrder({ "id", "name" })
public class JsonModel {
    @JsonAlias(value = "ID_")
//    @JsonProperty(value = "id_json")
    public int id;

    public String name;

    @JsonAlias(value = "cards_json")
    public List<CardModel> cards;
//
//    @JacksonXmlElementWrapper(useWrapping = false)
//    @JacksonXmlProperty(localName = "title")
//    private List<String> titles;

}
