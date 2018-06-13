package io.github.xesam.geo2district;

import com.google.gson.annotations.SerializedName;
import io.github.xesam.geo.Point;
import io.github.xesam.geo.Relation;
import io.github.xesam.geo2district.data.GeoSource;

import java.util.Optional;

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
    @SerializedName("center")
    private Point center;
    private Boundary boundary;

    public District(String adcode) {
        this.adcode = adcode;
    }

    public String getAdcode() {
        return adcode;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Point getCenter() {
        return center;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    public void inflateBoundary(GeoSource geoSource) {
        if (boundary != null) {
            return;
        }
        Optional<Boundary> boundaryOptional = geoSource.load(getAdcode());
        boundaryOptional.ifPresent(boundary1 -> this.boundary = boundary1);
    }

    public Relation relationOf(Point point) {
        if (boundary == null) {
            throw new RuntimeException("no boundary found");
        }
        return boundary.relationOf(point);
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
