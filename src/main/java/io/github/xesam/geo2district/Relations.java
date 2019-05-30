package io.github.xesam.geo2district;

import io.github.xesam.gis.core.Coordinate;
import io.github.xesam.gis.core.Relation;

import java.util.Iterator;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
class Relations {

    static Relation getRelation(Coordinate target, List<Coordinate> boundary) {
        int crossCount = 0;
        if (boundary.size() < 3) {
            return Relation.OUT;
        }
        Iterator<Coordinate> iterator = boundary.iterator();
        Coordinate base = iterator.next();
        while (iterator.hasNext()) {
            Coordinate next = iterator.next();
            if (isSamePoint(target, base) || isSamePoint(target, next)) {
                return Relation.OUT;
            }
            if ((base.getLatitude() < target.getLatitude() && next.getLatitude() >= target.getLatitude())
                    || (base.getLatitude() >= target.getLatitude() && next.getLatitude() < target.getLatitude())) {
                double crossLng = next.getLongitude() - (next.getLatitude() - base.getLatitude()) * (next.getLongitude() - base.getLongitude()) / (next.getLatitude() - base.getLatitude());

                if (crossLng == target.getLongitude()) {
                    return Relation.OUT;
                }
                if (crossLng < target.getLongitude()) {
                    crossCount++;
                }
            }

            base = next;
        }
        return (crossCount % 2 != 0) ? Relation.IN : Relation.OUT;
    }

    private static boolean isSamePoint(Coordinate coordinateA, Coordinate coordinateB) {
        return coordinateA.getLongitude() == coordinateB.getLongitude() && coordinateA.getLatitude() == coordinateB.getLatitude();
    }
}
