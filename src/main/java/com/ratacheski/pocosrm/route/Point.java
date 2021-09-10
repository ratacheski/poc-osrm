package com.ratacheski.pocosrm.route;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Point {
    private Double lat;
    private Double lon;

    @Override
    public String toString() {
        return lon + "," + lat;
    }
}
