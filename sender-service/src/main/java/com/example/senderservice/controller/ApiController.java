package com.example.senderservice.controller;

import com.example.senderservice.dto.EntityModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = "application/xml")
public class ApiController {

    @GetMapping(value = "/getXml")
    public EntityModel getXml() {
        EntityModel model = EntityModel.builder()
                .ID("1")
                .NAME("Darshan.G.Pawar")
                .DOB("05-09-2001")
                .PINCODE("422 009")
                .build();
        return model;
    }
}
