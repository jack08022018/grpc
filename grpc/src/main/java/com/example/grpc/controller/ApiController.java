package com.example.grpc.controller;


import com.example.grpc.dto.TransactionDto;
import com.example.grpc.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {

    final ApiService apiService;
//    final SenderDao senderDao;

    @PostMapping(value = "/deduct")
    public void deduct(@RequestBody TransactionDto dto) throws Exception {
        apiService.deduct(dto);
    }

//    @GetMapping(value = "/getAllTransaction")
//    public <T> T getAllTransaction() {
//        return (T) apiService.getAllTransaction();
//    }
//
//    @PostMapping(value = "/credit")
//    public void credit(@RequestBody TransactionDto dto) throws Exception {
//        apiService.credit(dto);
//    }
//
//    @PostMapping(value = "/createNativeQuery")
//    public void createNativeQuery(@RequestBody TransactionDto dto) {
//        senderDao.createNativeQuery(dto.getAccountId());
//    }
//
//    @PostMapping(value = "/changeFlag")
//    public void changeFlag(@RequestParam String status) throws Exception {
//        apiService.changeFlag(status);
//    }

}
