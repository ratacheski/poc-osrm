package com.ratacheski.pocosrm.core.osrm.model;

import lombok.Data;

@Data
public class Leg {
    private Double distance;
    private Double duration;
    private Double weight;
    private String summary;
}
