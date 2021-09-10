package com.ratacheski.pocosrm.route;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Soluction {
    List<ResponseRoute> routes = new ArrayList<>();
    List<ResponseWaypoint> waypoints = new ArrayList<>();
}
