package io.github.xesam.geo2district;

import com.google.gson.annotations.SerializedName;
import io.github.xesam.gis.core.Coordinate;
import io.github.xesam.gis.core.Relation;

/**
 * 区域
 *
 * @author xesamguo@gmail.com
 */
public class District {

    @SerializedName("adcode")
    private String adcode = "";
    @SerializedName("name")
    private String name = "";
    private Coordinate center = new Coordinate(-1, -1);
    private Boundary boundary;

    public District() {
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCenter() {
        return center;
    }

    public void setCenter(Coordinate center) {
        this.center = center;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    public Relation relationOf(Coordinate coordinate) {
        if (boundary == null) {
            return Relation.OUT;
        }
        return boundary.relationOf(coordinate);
    }

    @Override
    public String toString() {
        return "District{" +
                "adcode='" + adcode + '\'' +
                ", name='" + name + '\'' +
                ", center=" + center +
                ", boundary=" + boundary +
                '}';
    }
}
