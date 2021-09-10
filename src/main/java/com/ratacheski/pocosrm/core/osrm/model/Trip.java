package com.ratacheski.pocosrm.core.osrm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Trip {
    private String geometry;
    private List<Leg> legs;
    private Double distance;
    private Double duration;
    @JsonProperty("weight_name")
    private String weightName;
    private Double weight;
}
