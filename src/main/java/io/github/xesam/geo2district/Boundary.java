package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
import io.github.xesam.geo.Relation;
import io.github.xesam.geo.Relations;

import java.util.LinkedList;
import java.util.List;

/**
 * 边界集合，一个行政区可能有多个不接壤的边界
 *
 * @author xesamguo@gmail.com
 */
public class Boundary {

    private List<List<Point>> boundaries;

    public Boundary() {
        this(new LinkedList<>());
    }

    public Boundary(List<List<Point>> boundaries) {
        this.boundaries = boundaries;
    }

    @Deprecated
    public void add(List<Point> polygon) {
        boundaries.add(polygon);
    }

    public Relation relationOf(Point point) {
        for (List<Point> points : boundaries) {
            Relation relation = Relations.getRelation(point, points);
            if (relation == Relation.ON || relation == Relation.IN) {
                return relation;
            }
        }
        return Relation.OUT;
    }
}
