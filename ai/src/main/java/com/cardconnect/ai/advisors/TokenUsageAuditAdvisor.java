package com.cardconnect.ai.advisors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

@Slf4j
public class TokenUsageAuditAdvisor implements CallAdvisor {

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        ChatResponse chatResponse = chatClientResponse.chatResponse();
        Usage usage = chatResponse.getMetadata().getUsage();

        if(usage != null) {
            log.debug("Token usage details : {}",usage.toString());
        }
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "TokenUsageAuditAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }

    public static TokenUsageAuditAdvisor.Builder builder() {
        return new TokenUsageAuditAdvisor.Builder();
    }

    public static final class Builder {

        private Builder() {
        }

        public TokenUsageAuditAdvisor build() {
            return new TokenUsageAuditAdvisor();
        }

    }
}