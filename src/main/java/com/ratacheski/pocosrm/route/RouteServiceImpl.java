package com.ratacheski.pocosrm.route;

import com.ratacheski.pocosrm.core.osrm.model.OsrmTripResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

@Service
public class RouteServiceImpl implements RouteService {


    private final WebClient webClient;

    @Value("${osrm.api.trip_uri}")
    private String TRIP_URI;

    @Value("${osrm.api.route_uri}")
    private String ROUTE_URI;

    @Value("${osrm.api.request_profile}")
    private String OSRM_PROFILE;

    public RouteServiceImpl(@Value("${osrm.api.base_url}") String osrmBaseUrl, WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
        this.webClient = builder.baseUrl(osrmBaseUrl).
                defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE).
                clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Override
    public Soluction getTripSoluction(Problem problem) {
        if (problem.getComputeBestOrder()) {
            return getTrip(problem);
        }
        return getRoute(problem);
    }

    private Soluction getTrip(Problem problem) {
        OsrmTripResponse response = this.webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(getTripPath(problem));
                    uriBuilder.queryParam("overview", "false");
                    if (problem.getFixDeparture())
                        uriBuilder.queryParam("source", "first");
                    if (problem.getFixArrival())
                        uriBuilder.queryParam("destination", "last");
                    if (!problem.getRoundTrip())
                        uriBuilder.queryParam("roundtrip","false");
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(OsrmTripResponse.class)
                .block();
        return osrmTripResponseToSoluction(response, problem);
    }


    private String getTripPath(Problem problem) {
        StringBuilder builder = new StringBuilder();
        builder.append(TRIP_URI);
        builder.append(OSRM_PROFILE);
        builder.append("/");
        builder.append(problem.getpointListAsLocationsString());
        return builder.toString();
    }

    private Soluction getRoute(Problem problem) {
        OsrmTripResponse response = this.webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(getRoutePath(problem));
                    uriBuilder.queryParam("overview", "false");
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(OsrmTripResponse.class)
                .block();
        return osrmTripResponseToSoluction(response, problem);
    }

    private String getRoutePath(Problem problem) {
        StringBuilder builder = new StringBuilder();
        builder.append(ROUTE_URI);
        builder.append(OSRM_PROFILE);
        builder.append("/");
        builder.append(problem.getpointListAsLocationsString());
        return builder.toString();
    }

    private Soluction osrmTripResponseToSoluction(OsrmTripResponse response, Problem problem) {
        Soluction soluction = new Soluction();
        for (int i = 0; i < problem.getPointList().size(); i++) {
            soluction.getWaypoints().add(new ResponseWaypoint(response.getWaypoints().get(i),problem.getPointList().get(i), i));
        }
        if (problem.getComputeBestOrder())
            soluction.getWaypoints().sort(Comparator.comparing(ResponseWaypoint::getOptimizedIndex));
        ResponseRoute route = new ResponseRoute();
        route.setDistance(response.getTrips().get(0).getDistance());
        route.setDuration(response.getTrips().get(0).getDuration());
        route.setDepartureTime(OffsetDateTime.now());
        route.setArrivalTime(route.getDepartureTime().plus(route.getDuration().longValue(), ChronoUnit.SECONDS));
        soluction.getRoutes().add(route);
        return soluction;
    }
}
