package io.github.xesam.geo;

/**
 * @author xesamguo@gmail.com
 */
public class Point {
    private final double lng;
    private final double lat;

    public Point(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }
}
