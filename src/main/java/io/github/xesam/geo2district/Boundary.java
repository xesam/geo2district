package io.github.xesam.geo2district;

import java.util.LinkedList;
import java.util.List;

/**
 * 边界集合，一个行政区可能有多个不接壤的边界
 *
 * @author xesamguo@gmail.com
 */
public class Boundary {

    private List<SubBoundary> subs = new LinkedList<>();

    private List<List<GeoPoint>> boundaries = new LinkedList<>();

    public void add(List<GeoPoint> polygon) {
        boundaries.add(polygon);
    }

    public List<List<GeoPoint>> value() {
        return boundaries;
    }
}
