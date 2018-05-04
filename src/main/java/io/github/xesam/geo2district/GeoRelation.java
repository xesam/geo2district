package io.github.xesam.geo2district;

import java.util.Iterator;
import java.util.List;

/**
 * created by xesamguo@gmail.com
 */
public class GeoRelation {

    public Relation getRelation(GeoPoint target, List<GeoPoint> boundary) {
        int crossCount = 0;
        if (boundary.size() < 3) {
            return Relation.OUT;
        }
        Iterator<GeoPoint> iterator = boundary.iterator();
        GeoPoint base = iterator.next();
        while (iterator.hasNext()) {
            GeoPoint next = iterator.next();
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

    private boolean isSamePoint(GeoPoint pointA, GeoPoint pointB) {
        return pointA.getLng() == pointB.getLng() && pointA.getLat() == pointB.getLat();
    }
}
