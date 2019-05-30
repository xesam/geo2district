package io.github.xesam.gis.core;

/**
 * @author xesamguo@gmail.com
 */
public class Coordinate {
    private final CoordinateType type;
    private final double longitude;
    private final double latitude;

    public Coordinate(double longitude, double latitude) {
        this(CoordinateType.WGS84, longitude, latitude);
    }

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

    @Override
    public String toString() {
        return "Coordinate{" +
                "type=" + type +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

}
