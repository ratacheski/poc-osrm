package com.ratacheski.pocosrm.route;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Problem {
    private List<Point> pointList;
    private Boolean computeBestOrder;
    private Boolean fixDeparture;
    private Boolean fixArrival;
    private Boolean roundTrip;


    public Problem(String locations, Boolean computeBestOrder, Boolean fixArrival, Boolean fixDeparture, Boolean roundTrip) {
        this.pointList = new ArrayList<>();
        String[] split = locations.split(":");
        Arrays.stream(split).forEach(s -> {
            String[] point = s.split(",");
            Double lat = Double.valueOf(point[0]);
            Double lon = Double.valueOf(point[1]);
            this.pointList.add(new Point(lat, lon));
        });
        this.computeBestOrder = computeBestOrder;
        this.fixDeparture = fixDeparture;
        this.fixArrival = fixArrival;
        this.roundTrip = roundTrip;
    }

    public String getpointListAsLocationsString() {
        return pointList.stream().map(Point::toString).reduce((s, s2) -> s + ";" + s2).get();
    }
}
