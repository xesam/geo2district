package io.github.xesam.geo2district.files;

import com.google.gson.annotations.SerializedName;
import io.github.xesam.gis.core.Coordinate;

class Properties {
    @SerializedName("name")
    public String name;
    @SerializedName("adcode")
    public String adcode;
    @SerializedName("center")
    public Coordinate center;
}
