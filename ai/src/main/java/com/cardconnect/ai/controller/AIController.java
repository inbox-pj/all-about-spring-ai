package com.cardconnect.ai.controller;

import com.cardconnect.ai.model.RestResponse;
import com.cardconnect.ai.service.AIService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AIController {

    private final AIService service;

    @GetMapping(value = "/shipment/inquiry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> inquire(@Valid @NotNull @RequestParam("shipmentId") String shipmentId, @RequestParam("message") String message) {
        log.info("Request Received for shipmentId: {}", shipmentId);
        return ResponseEntity.ok(service.doWork(shipmentId, message));
    }
}
