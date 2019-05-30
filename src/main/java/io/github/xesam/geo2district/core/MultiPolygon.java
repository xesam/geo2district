package io.github.xesam.geo2district.core;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
public class MultiPolygon {
    @SerializedName("coordinates")
    private List<Polygon> coordinates = new ArrayList<>();

    public MultiPolygon(List<Polygon> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Polygon> getCoordinates() {
        return coordinates;
    }
}
