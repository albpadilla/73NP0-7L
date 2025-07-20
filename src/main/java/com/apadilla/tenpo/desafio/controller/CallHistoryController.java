package com.apadilla.tenpo.desafio.controller;

import com.apadilla.tenpo.desafio.controller.dto.CallHistoryResponse;
import com.apadilla.tenpo.desafio.controller.mapper.CallHistoryMapper;
import com.apadilla.tenpo.desafio.entity.CallHistory;
import com.apadilla.tenpo.desafio.service.CallHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/call-history")
@Slf4j
public class CallHistoryController {

    private final CallHistoryService callHistoryService;
    private final CallHistoryMapper callHistoryMapper;

    public CallHistoryController(
            CallHistoryService callHistoryService,
            CallHistoryMapper callHistoryMapper
    ) {
        this.callHistoryService = callHistoryService;
        this.callHistoryMapper = callHistoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CallHistoryResponse>> getCallHistory(Pageable pageable) {
        List<CallHistoryResponse> responses = this.callHistoryService.findAllCallHistory(pageable)
                .getContent()
                .stream()
                .map(callHistoryMapper::mapEntityToDTOResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

}

