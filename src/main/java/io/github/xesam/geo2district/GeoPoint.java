package io.github.xesam.geo2district;

public class GeoPoint {
    private final double lng;
    private final double lat;

    public GeoPoint(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public GeoPoint(double[] vals) {
        this(vals[0], vals[1]);
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }
}
