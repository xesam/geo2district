package io.github.xesam.geo2district;

import com.google.gson.annotations.SerializedName;
import io.github.xesam.gis.core.Coordinate;
import io.github.xesam.gis.core.Relation;
import io.github.xesam.geo2district.data.BoundarySource;

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
    private Coordinate center = new Coordinate(-1, -1);
    private Boundary boundary;

    public District() {
    }

    public String getAdcode() {
        return adcode;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCenter() {
        return center;
    }

    public void inflateBoundary(BoundarySource boundarySource) {
        if (boundary != null) {
            return;
        }
        Optional<Boundary> boundaryOptional = boundarySource.load(getAdcode());
        boundaryOptional.ifPresent(boundary1 -> this.boundary = boundary1);
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
