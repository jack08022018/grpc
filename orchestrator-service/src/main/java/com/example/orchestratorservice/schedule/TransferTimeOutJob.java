package com.example.orchestratorservice.schedule;

import com.example.orchestratorservice.service.OrchestrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
//@Component
@RestController
@RequiredArgsConstructor
public class TransferTimeOutJob {

    private final OrchestrationService orchestrationService;

    @Scheduled(cron = "0 0/5 * * * *")
    @GetMapping(value = "/runJob")
    public void executeTransferTimeOutJob() {
        log.info("Execute Transfer TimeOut Job start at: {}", new Date());
        orchestrationService.reExecuteTransferMoneyWorkflow();
        log.info("Execute Transfer TimeOut Job end at: {}", new Date());
    }

}