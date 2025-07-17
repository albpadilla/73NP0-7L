package com.apadilla.tenpo.desafio.client;

import com.apadilla.tenpo.desafio.client.dto.PercentageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "percentage-service", url = "${percentage.service.url:http://localhost:8081}")
public interface PercentageClient {

    @GetMapping("/percentage")
    PercentageResponse getPercentage();
}
