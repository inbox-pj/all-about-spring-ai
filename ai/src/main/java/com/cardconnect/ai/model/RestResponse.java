package com.cardconnect.ai.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse {
    private String message;
    private String shipmentId;
    private String origin;
    private String destination;
    private String weight;
    private String shipmentDate;
    private String status;
    private String priority;
    private String currentTime;
    private String currentWeather;
}
