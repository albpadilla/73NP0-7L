package com.apadilla.tenpo.desafio.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CallHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "endpoint", nullable = false, length = 500)
    private String endpoint;

    @Column(name = "http_method", nullable = false, length = 10)
    private String httpMethod;

    @Column(name = "request_parameters", columnDefinition = "TEXT")
    private String requestParameters;

    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "response_status")
    private Integer responseStatus;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
}
