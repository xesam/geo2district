package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * 边界集合，一个行政区可能有多个不接壤的边界
 *
 * @author xesamguo@gmail.com
 */
public class Boundary {

    private List<SubBoundary> subs = new LinkedList<>();

    private List<List<Point>> boundaries = new LinkedList<>();

    public void add(List<Point> polygon) {
        boundaries.add(polygon);
    }

    public List<List<Point>> value() {
        return boundaries;
    }
}
