package com.cardconnect.ai.service.impl;

import com.cardconnect.ai.model.ActorFilms;
import com.cardconnect.ai.service.AIService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AIServiceImpl implements AIService {

    private final ChatClient chatClient;

    @Override
    public Flux<String> doWorkStream(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
    
    @Override
    public List<ActorFilms> doWork(String message) {

        return chatClient.prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>() {
                });
    }

}
