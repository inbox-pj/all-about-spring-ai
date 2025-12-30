package com.cardconnect.ai.controller;

import com.cardconnect.ai.model.ActorFilms;
import com.cardconnect.ai.service.AIService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/imperative")
@AllArgsConstructor
public class AIController {

    private final AIService service;


    @GetMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> chat(@Valid @NotNull @RequestParam("message") String message) {
        return service.doWorkStream(message);
    }

    @GetMapping(value = "/getActorFilms", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActorFilms> getActorFilms(@Valid @NotNull @RequestParam("message") String message) {
        return service.doWork(message);
    }

}
