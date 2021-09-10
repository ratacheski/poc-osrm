package com.ratacheski.pocosrm.route;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteControllerImpl implements RouteController {

    private RouteService routeService;

    public RouteControllerImpl(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Soluction route(String locations, Boolean computeBestOrder, Boolean fixDeparture, Boolean fixArrival, Boolean roundTrip) {
        Problem problem = new Problem(locations, computeBestOrder, fixDeparture, fixArrival, roundTrip);
        return routeService.getTripSoluction(problem);
    }
}
