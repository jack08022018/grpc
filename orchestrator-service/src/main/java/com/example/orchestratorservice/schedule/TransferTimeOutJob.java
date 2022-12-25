package com.example.orchestratorservice.schedule;

import com.example.orchestratorservice.service.OrchestrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransferTimeOutJob {

    private final OrchestrationService orchestrationService;

    @Scheduled(cron = "0 0/5 * ? * *")
//    @Scheduled(cron = "0/20 * * ? * *")
    public void executeTransferTimeOutJob() {

        log.info("Execute Transfer TimeOut Job start at: {}", new Date());
        orchestrationService.reExecuteTransferMoneyWorkflow();
        log.info("Execute Transfer TimeOut Job end at: {}", new Date());
    }

}