package com.ratacheski.pocosrm.route;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ResponseRoute {
    private OffsetDateTime departureTime;
    private OffsetDateTime arrivalTime;
    private Double distance;
    private Double duration;
}
