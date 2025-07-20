package com.apadilla.tenpo.desafio.service.impl;

import com.apadilla.tenpo.desafio.entity.CallHistory;
import com.apadilla.tenpo.desafio.repository.CallHistoryRepository;
import com.apadilla.tenpo.desafio.service.CallHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CallHistoryServiceImpl implements CallHistoryService {

    private final CallHistoryRepository callHistoryRepository;

    public CallHistoryServiceImpl(CallHistoryRepository callHistoryRepository) {
        this.callHistoryRepository = callHistoryRepository;
    }

    @Async
    public void saveCallHistoryAsync(CallHistory callHistory) {
        try {
            CallHistory saved = this.callHistoryRepository.save(callHistory);
            CompletableFuture.completedFuture(saved);
        } catch (Exception e) {
            log.error("error saving call history", e);
            CompletableFuture.failedFuture(e);
        }
    }

    public Page<CallHistory> findAllCallHistory(Pageable pageable) {
        return this.callHistoryRepository.findAll(pageable);
    }

}
