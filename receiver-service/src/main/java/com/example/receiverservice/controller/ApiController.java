package com.example.receiverservice.controller;


import com.example.receiverservice.dto.JsonModel;
import com.example.receiverservice.dto.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

//    @GetMapping(value = "/jsonToXml", produces = "application/xml")
    @GetMapping(value = "/jsonToXml")
    public <T> T jsonToXml() throws JsonProcessingException {
        String xmlString= """
                <user id="1">
                    <NAME_XML>foo</NAME_XML>
                    <cards>
                         <card>
                            <id>6</id>
                            <title>C1</title>
                         </card>
                         <card>
                            <id>7</id>
                            <title>C2</title>
                         </card>
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

    @GetMapping(value = "/stringToJson")
    public <T> T stringToJson() throws JsonProcessingException {
        String jsonString = """
                {
                   "ID_": 1,
                   "name": "foo",
                   "cards_json": [
                       {"ID_": 6, "title": "C1"},
                       {"ID_": 7, "title": "C2"}
                   ]
               }""";
//        JsonNode jsonNode = new ObjectMapper().readTree(jsonString);
        JsonModel jsonModel = customObjectMapper.readValue(jsonString, JsonModel.class);
        return (T) jsonModel;
    }

    @GetMapping(value = "/sacom")
    public <T> T sacom(@RequestBody RequestDto<RequestFile> requestDto) throws JsonProcessingException {
        RequestFile file = requestDto.data;
        String xmlString = """
                <EBK_INT_CMS_0006_REQ>
                   <HEADER>
                       <MTI>1210</MTI>
                   </HEADER>
                   <BODY>
                       <CARDCODE>3964302001</CARDCODE>
                       <CARDNUMBER>472075XXXXXX9598</CARDNUMBER>
                       <DOCDTLS>
                           <DOCID>396430XXXXXX221231</DOCID>
                           <STMTDATE>20221231</STMTDATE>
                       </DOCDTLS>
                       <DOCDTLS>
                           <DOCID>396430XXXXXX221130</DOCID>
                           <STMTDATE>20221130</STMTDATE>
                       </DOCDTLS>
                   </BODY>
               </EBK_INT_CMS_0006_REQ>""";
        ObjectNode objectNode = new XmlMapper().readValue(xmlString, ObjectNode.class);
//        XmlMapper xmlMapper = new XmlMapper();
//        UserModel poppy = xmlMapper.readValue(xmlString, UserModel.class);
        return (T) objectNode;
    }

}
