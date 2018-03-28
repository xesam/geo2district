package io.github.xesam.geo2district;

import java.util.LinkedList;
import java.util.List;

/**
 * 行政区
 */
public class District {
    public static final class Type {
        public static final int NORMAL_PROVINCE = 1;//省
        public static final int AUTONOMOUS = 2; //自治区
        public static final int MUNICIPALITY = 3; //直辖市
        public static final int SPECIAL = 4; //特别行政区
    }

    private String id;
    private String name;
    private List<List<GeoPoint>> boundaries = new LinkedList<>();

    public District() {
    }

    public District(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
