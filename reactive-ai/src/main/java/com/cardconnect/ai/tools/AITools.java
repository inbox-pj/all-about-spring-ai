package com.cardconnect.ai.tools;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AITools {

    @Tool(name = "getTime", description = "Get the current time in a specified city. Input should be a valid city name.")
    public String getTime(@ToolParam(required = true, description = "city name") String city, ToolContext context) {
        String shipmentId = (String) context.getContext().get("shipmentId");
        log.info("{} : Getting current time in : {}", shipmentId, city);
        try {
            java.time.ZoneId zoneId = java.time.ZoneId.of("America/New_York");
            java.time.ZonedDateTime zonedDateTime = java.time.ZonedDateTime.now(zoneId);
            return "The current time in " + city + " is " + zonedDateTime.toString();
        } catch (Exception e) {
            log.error("Error getting time for {}: {}", city, e.getMessage());
            return "Invalid timezone: " + "America/New_York";
        }
    }

    @Tool(name = "getWeather", description = "Get the current weather for a specified city. Input should be a valid city name.")
    public String getWeather(@ToolParam(required = true, description = "city name") String city, ToolContext context) {
        String shipmentId = (String) context.getContext().get("shipmentId");
        log.info("Getting weather for city: {}", city);
        // Dummy implementation, replace with actual weather fetching logic
        return "The current weather in " + city + " is Sunny, 25°C";
    }
}

