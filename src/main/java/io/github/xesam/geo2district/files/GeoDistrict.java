package io.github.xesam.geo2district.files;

import com.google.gson.annotations.SerializedName;

class GeoDistrict {
    @SerializedName("properties")
    public Properties properties;
    @SerializedName("geometry")
    public Geometry geometry;
}
