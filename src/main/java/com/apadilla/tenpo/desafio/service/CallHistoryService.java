package com.apadilla.tenpo.desafio.service;

import com.apadilla.tenpo.desafio.entity.CallHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CallHistoryService {

    void saveCallHistoryAsync(CallHistory callHistory);

    Page<CallHistory> findAllCallHistory(Pageable pageable);
}
