package io.github.xesam.geo2district;

import io.github.xesam.gis.core.Coordinate;
import io.github.xesam.geo.Relations;
import io.github.xesam.gis.core.Relation;

import java.util.LinkedList;
import java.util.List;

/**
 * 边界集合，一个行政区可能有多个不接壤的边界
 *
 * @author xesamguo@gmail.com
 */
public class Boundary {

    private List<Polygon> polygons;

    public Boundary() {
        this(new LinkedList<>());
    }

    public Boundary(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public Relation relationOf(Coordinate coordinate) {
        for (Polygon polygon : polygons) {
            Relation relation = polygon.relationOf(coordinate);
            if (relation == Relation.ON || relation == Relation.IN) {
                return relation;
            }
        }
        return Relation.OUT;
    }

    /**
     * @author xesamguo@gmail.com
     */
    public static class Polygon {

        private List<Coordinate> coordinates;

        public Polygon(List<Coordinate> coordinates) {
            this.coordinates = coordinates;
        }

        public Relation relationOf(Coordinate coordinate) {
            Relation relation = Relations.getRelation(coordinate, coordinates);
            if (relation == Relation.ON || relation == Relation.IN) {
                return relation;
            }
            return Relation.OUT;
        }
    }
}
