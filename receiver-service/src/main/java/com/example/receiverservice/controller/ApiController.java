package com.example.receiverservice.controller;


import com.example.receiverservice.dto.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {

    final RestTemplate customRestTemplate;
    final ObjectMapper customObjectMapper;

    @GetMapping(value = "/getXml")
    public <T> T changeFlag() throws JsonProcessingException {
        String url = "http://localhost:9001/sender/api/getXml";
        UserModel jsonNode = customRestTemplate.getForObject(url, UserModel.class);
        return (T) jsonNode;
    }

    @GetMapping(value = "/jsonToXml", produces = "application/xml")
//    @GetMapping(value = "/jsonToXml")
    public <T> T jsonToXml() throws JsonProcessingException {
        String xmlString= """
                <user id="1">
                    <NAME_XML>foo</NAME_XML>
                    <cards>
                         <card>C1</card>
                         <card>C2</card>
                    </cards>
                    <title>T1</title>
                    <title>T2</title>
                </user>""";
        XmlMapper xmlMapper = new XmlMapper();
        UserModel poppy = xmlMapper.readValue(xmlString, UserModel.class);
        return (T) poppy;
    }

    @GetMapping(value = "/xmlToJson")
    public <T> T xmlToJson() throws JsonProcessingException {
        String jsonString = """
                {
                   "cards": [
                       "C1",
                       "C2"
                   ],
                   "name": "foo",
                   "id": 1,
                   "titles": [
                        "T2",
                        "T2"
                   ]
               }""";
        ObjectNode objectNode = new JsonMapper().readValue(jsonString, ObjectNode.class);
        String xml = new XmlMapper().writeValueAsString(objectNode);
        return (T) xml;
    }

}
