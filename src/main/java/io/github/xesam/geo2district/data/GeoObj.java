package io.github.xesam.geo2district.data;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
class GeoObj {
    @SerializedName("geometry")
    private MultiPolygon geometry;

    public Optional<MultiPolygon> getGeometry() {
        return Optional.ofNullable(geometry);
    }
}
