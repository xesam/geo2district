package io.github.xesam.geo2district;

import com.google.gson.annotations.SerializedName;
import io.github.xesam.geo.Point;
import io.github.xesam.geo.Relation;
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
    private Point center = new Point(-1, -1);
    private Boundary boundary;

    public District() {
    }

    public String getAdcode() {
        return adcode;
    }

    public String getName() {
        return name;
    }

    public Point getCenter() {
        return center;
    }

    public void inflateBoundary(BoundarySource boundarySource) {
        if (boundary != null) {
            return;
        }
        Optional<Boundary> boundaryOptional = boundarySource.load(getAdcode());
        boundaryOptional.ifPresent(boundary1 -> this.boundary = boundary1);
    }

    public Relation relationOf(Point point) {
        if (boundary == null) {
            return Relation.OUT;
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
