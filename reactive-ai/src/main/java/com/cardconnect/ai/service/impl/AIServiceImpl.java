package com.cardconnect.ai.service.impl;

import com.cardconnect.ai.model.RestResponse;
import com.cardconnect.ai.service.AIService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class AIServiceImpl implements AIService {

    private final ChatClient chatClient;

    @Override
    public RestResponse doWork(String shipmentId, String message) {
        BeanOutputConverter<RestResponse> converter = new BeanOutputConverter(RestResponse.class);

        String chatResponse = chatClient.prompt()
                .advisors(as ->
                        as.param(ChatMemory.CONVERSATION_ID, shipmentId))
                .user(ut ->
                        ut.param("shipmentId", shipmentId)
                                .param("customerMessage", message)
                )
                .toolContext(Map.of("shipmentId", shipmentId))  // pass @ToolsParam as part of Tool Context
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();

        return converter.convert(chatResponse);
    }
}
