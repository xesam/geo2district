package io.github.xesam.geo2district.files;

import com.google.gson.annotations.SerializedName;
import io.github.xesam.geo2district.core.MultiPolygon;

/**
 * @author xesamguo@gmail.com
 */
class Geometry {
    @SerializedName("type")
    public String type;
    @SerializedName("coordinates")
    public MultiPolygon geometry;
}
