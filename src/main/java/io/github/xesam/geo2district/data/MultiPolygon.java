package io.github.xesam.geo2district.data;

import com.google.gson.annotations.SerializedName;
import io.github.xesam.geo.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
class MultiPolygon {
    @SerializedName("coordinates")
    private List<List<Point>> coordinates = new ArrayList<>();

    public List<List<Point>> getCoordinates() {
        return coordinates;
    }
}
