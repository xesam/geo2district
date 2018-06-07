package io.github.xesam.geo2district;

/**
 * @author xesamguo@gmail.com
 */
public class GeoPoint {
    private final double lng;
    private final double lat;

    public GeoPoint(double lng, double lat) {
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
