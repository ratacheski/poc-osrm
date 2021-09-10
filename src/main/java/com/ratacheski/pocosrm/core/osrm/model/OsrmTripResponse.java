package com.ratacheski.pocosrm.core.osrm.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class OsrmTripResponse {
    private String code;
    @JsonAlias({"trips", "routes"})
    private List<Trip> trips;
    private List<Waypoint> waypoints;
}
