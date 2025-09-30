package com.cardconnect.ai.config;

import com.cardconnect.ai.tools.ShipmentHelpDeskRemoteTools;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class McpServerConfig {

    @Bean
    List<ToolCallback> toolCallbacks(ShipmentHelpDeskRemoteTools helpDeskTools) {
        return List.of(ToolCallbacks.from(helpDeskTools));
    }
}