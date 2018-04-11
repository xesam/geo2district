package io.github.xesam.geo2district;

import java.util.LinkedList;
import java.util.List;

/**
 * 区域
 */
public class District {

    private String id;
    private String name;
    private List<List<GeoPoint>> boundaries = new LinkedList<>();

    public District(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public List<List<GeoPoint>> getBoundaries() {
        return boundaries;
    }

    public void addBoundary(List<GeoPoint> polygon) {
        boundaries.add(polygon);
    }

    @Override
    public String toString() {
        return "District{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", boundaries.size=" + boundaries.size() +
                '}';
    }
}
