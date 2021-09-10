package com.ratacheski.pocosrm.core.osrm.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Waypoint {
    @JsonProperty("waypoint_index")
    private Integer waypointIndex;
    @JsonProperty("trips_index")
    private Integer tripsIndex;
    private List<Double> location;
    private String name;
    private Double distance;
    private String hint;
}
