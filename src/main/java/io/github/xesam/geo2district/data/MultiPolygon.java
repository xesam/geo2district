package io.github.xesam.geo2district.data;

import com.google.gson.annotations.SerializedName;
import io.github.xesam.geo2district.Boundary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
class MultiPolygon {
    @SerializedName("coordinates")
    private List<Boundary.Polygon> coordinates = new ArrayList<>();

    public List<Boundary.Polygon> getCoordinates() {
        return coordinates;
    }
}
