package com.ratacheski.pocosrm.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ratacheski.pocosrm.core.osrm.model.Waypoint;
import lombok.Data;

@Data
public class ResponseWaypoint {
    private Point originalLocation;
    private Point location;
    private Integer providedIndex;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer optimizedIndex;
    private String name;
    private Double distance;

    public ResponseWaypoint(Waypoint waypoint, Point originalLocation, Integer providedIndex) {
        this.name = waypoint.getName();
        this.location = new Point(waypoint.getLocation().get(1), waypoint.getLocation().get(0));
        this.originalLocation = originalLocation;
        this.providedIndex = providedIndex;
        if (waypoint.getWaypointIndex() != null)
            this.optimizedIndex = waypoint.getWaypointIndex();
        this.distance = waypoint.getDistance();
    }
}
