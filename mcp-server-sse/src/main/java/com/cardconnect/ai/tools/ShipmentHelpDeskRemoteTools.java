package com.cardconnect.ai.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component

public class ShipmentHelpDeskRemoteTools {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentHelpDeskRemoteTools.class);

    @Tool(description = "Fetch the status of the tickets based on a given Shipment Ticket ID")
    String getTicketStatus(@ToolParam(description = "tracking Id of the shipment") String trackingId, ToolContext toolContext) {
        String shipmentId = (String) toolContext.getContext().get("shipmentId");
        LOGGER.info("Fetching tickets for shipmentId: {}", shipmentId);
        return "Status for Shipment - " + shipmentId + ", Ticket #" + trackingId + " Status is OPEN";
    }

}
