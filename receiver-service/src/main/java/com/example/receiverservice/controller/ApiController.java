package com.example.receiverservice.controller;


import com.example.receiverservice.service.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {

    @GetMapping(value = "/changeFlag")
    public void changeFlag(@RequestParam String flag) throws Exception {
        switch (flag) {
            case "NORMAL":
                ReceiverService.flag = ReceiverService.flag.NORMAL;
                return;
            case "TIMEOUT":
                ReceiverService.flag = ReceiverService.flag.TIMEOUT;
                return;
        }
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
