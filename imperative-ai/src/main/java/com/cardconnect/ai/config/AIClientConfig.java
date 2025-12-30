package com.cardconnect.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIClientConfig {

    @Bean
    public ChatClient.Builder chatClientBuilder(OllamaChatModel chatModel) {
        return ChatClient.builder(chatModel);
    }

    @Bean
    public ChatClient ollamaChatClient(ChatClient.Builder builder) {
        return builder
                .defaultAdvisors(
                        SimpleLoggerAdvisor.builder().build()
                )
                .build();
    }


}
