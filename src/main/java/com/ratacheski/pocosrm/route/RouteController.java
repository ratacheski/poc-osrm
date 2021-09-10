package com.ratacheski.pocosrm.route;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("route")
public interface RouteController {
    @GetMapping("calculateRoute/{locations}")
    Soluction route(@PathVariable String locations,
                    @RequestParam(required = false, defaultValue = "false") Boolean computeBestOrder,
                    @RequestParam(required = false, defaultValue = "true") Boolean fixDeparture,
                    @RequestParam(required = false, defaultValue = "true") Boolean fixArrival,
                    @RequestParam(required = false, defaultValue = "false") Boolean roundTrip
                    );
}
