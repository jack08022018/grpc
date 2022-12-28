package com.order.controller;

import com.order.dto.TransferMoneyDto;
import com.order.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {
    private final ApiService apiService;

    @PostMapping("/transferMoney")
    public <T> T transferMoney(@RequestBody TransferMoneyDto dto) {
        apiService.transferMoneyWorkflow(dto);
        return (T) "success";
    }
}
