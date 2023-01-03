package com.example.controller;

import com.example.adapter.SenderAdapter;
import com.example.dto.TransferMoneyDto;
import com.example.service.ApiService;
import com.sender.HelloRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {
    private final SenderAdapter senderAdapter;
    private final ApiService apiService;

    @GetMapping("/test")
    public <T> T test() {
        HelloRequest request = HelloRequest.newBuilder()
                .setInput("Jack")
                .build();
        return (T) senderAdapter.hello(request).getOutput();
//        return (T) "success";
    }

    @PostMapping("/transferMoney")
    public <T> T transferMoney(@RequestBody TransferMoneyDto dto) {
        apiService.transferMoneyWorkflow(dto);
        return (T) "success";
    }
}
