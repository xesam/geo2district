package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
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

    public Relation relationOf(Point point) {
        for (Polygon polygon : polygons) {
            Relation relation = polygon.relationOf(point);
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

        private List<Point> points;

        public Polygon(List<Point> points) {
            this.points = points;
        }

        public Relation relationOf(Point point) {
            Relation relation = Relations.getRelation(point, points);
            if (relation == Relation.ON || relation == Relation.IN) {
                return relation;
            }
            return Relation.OUT;
        }
    }
}
