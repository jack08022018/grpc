package com.example.receiverservice.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JacksonXmlRootElement(localName = "user")
public class SacomModel {
    @JacksonXmlProperty(localName = "CARDNUMBER")
    private String cardNumber;

    @JacksonXmlElementWrapper(localName = "cards")
    @JacksonXmlProperty(localName = "card")
    private List<Card> cards;

    class Card {
        @JsonAlias(value = "ID_")
        private int id;

        private String title;
    }
}

