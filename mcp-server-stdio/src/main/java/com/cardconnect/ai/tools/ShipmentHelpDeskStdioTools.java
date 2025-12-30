package com.cardconnect.ai.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component

public class ShipmentHelpDeskStdioTools {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentHelpDeskStdioTools.class);

    @Tool(name = "createTicket", description = "Create the Support Ticket", returnDirect = true)
    public String createTicket(ToolContext toolContext) {
        String shipmentId = (String) toolContext.getContext().get("shipmentId");
        LOGGER.debug("{ShipmentHelpDeskStdioTools} -> Shipment ID: {}", shipmentId);
        return "Ticket #" + UUID.randomUUID() + " created successfully for ShipmentId " + shipmentId;
    }

}
