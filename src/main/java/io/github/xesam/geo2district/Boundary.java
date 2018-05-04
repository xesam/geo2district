package io.github.xesam.geo2district;

import java.util.LinkedList;
import java.util.List;

public class Boundary {

    private List<List<GeoPoint>> boundaries = new LinkedList<>();

    public void add(List<GeoPoint> polygon) {
        boundaries.add(polygon);
    }

    public List<List<GeoPoint>> value() {
        return boundaries;
    }
}
