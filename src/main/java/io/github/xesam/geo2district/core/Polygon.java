package io.github.xesam.geo2district.core;

import io.github.xesam.gis.core.Coordinate;
import io.github.xesam.gis.core.Relation;

import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
public class Polygon {

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
