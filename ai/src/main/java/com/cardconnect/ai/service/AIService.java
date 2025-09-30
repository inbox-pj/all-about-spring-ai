package com.cardconnect.ai.service;

import com.cardconnect.ai.model.RestResponse;

public interface AIService {
    RestResponse doWork(String shipmentId, String message);
}
