package io.github.xesam.geo;

import java.util.Iterator;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
public class Relations {

    public Relation getRelation(Point target, List<Point> boundary) {
        int crossCount = 0;
        if (boundary.size() < 3) {
            return Relation.OUT;
        }
        Iterator<Point> iterator = boundary.iterator();
        Point base = iterator.next();
        while (iterator.hasNext()) {
            Point next = iterator.next();
            if (isSamePoint(target, base) || isSamePoint(target, next)) {
                return Relation.OUT;
            }
            if ((base.getLat() < target.getLat() && next.getLat() >= target.getLat())
                    || (base.getLat() >= target.getLat() && next.getLat() < target.getLat())) {
                double crossLng = next.getLng() - (next.getLat() - base.getLat()) * (next.getLng() - base.getLng()) / (next.getLat() - base.getLat());

                if (crossLng == target.getLng()) {
                    return Relation.OUT;
                }
                if (crossLng < target.getLng()) {
                    crossCount++;
                }
            }

            base = next;
        }
        return (crossCount % 2 != 0) ? Relation.IN : Relation.OUT;
    }

    private boolean isSamePoint(Point pointA, Point pointB) {
        return pointA.getLng() == pointB.getLng() && pointA.getLat() == pointB.getLat();
    }
}
