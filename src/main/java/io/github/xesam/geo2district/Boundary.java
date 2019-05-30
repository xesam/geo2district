package io.github.xesam.geo2district;

import io.github.xesam.geo2district.core.MultiPolygon;
import io.github.xesam.geo2district.core.Polygon;
import io.github.xesam.gis.core.Coordinate;
import io.github.xesam.gis.core.Relation;

/**
 * 边界集合，一个行政区可能有多个不接壤的边界
 *
 * @author xesamguo@gmail.com
 */
public class Boundary {

    private MultiPolygon polygons;

    public Boundary(MultiPolygon polygon) {
        this.polygons = polygon;
    }

    public Relation relationOf(Coordinate coordinate) {
        for (Polygon polygon : polygons.getCoordinates()) {
            Relation relation = polygon.relationOf(coordinate);
            if (relation == Relation.ON || relation == Relation.IN) {
                return relation;
            }
        }
        return Relation.OUT;
    }

}
