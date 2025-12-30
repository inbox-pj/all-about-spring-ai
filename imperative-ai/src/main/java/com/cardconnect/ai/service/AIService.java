package com.cardconnect.ai.service;

import com.cardconnect.ai.model.ActorFilms;
import reactor.core.publisher.Flux;

import java.util.List;

public interface AIService {
    List<ActorFilms> doWork(String message);

    Flux<String> doWorkStream(String message);
}
