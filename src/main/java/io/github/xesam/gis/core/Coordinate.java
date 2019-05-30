package io.github.xesam.gis.core;

public class Coordinate {
    private CoordinateType type;
    private double longitude;
    private double latitude;

    public Coordinate(CoordinateType type, double longitude, double latitude) {
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CoordinateType getType() {
        return type;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
