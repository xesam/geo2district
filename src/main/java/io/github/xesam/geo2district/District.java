package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * 区域
 */
public class District {

    private String id;
    private String name;
    private Boundary boundary;
    @Deprecated
    private List<List<Point>> boundaries = new LinkedList<>();
    private List<District> districts = new LinkedList<>();

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

    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
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
