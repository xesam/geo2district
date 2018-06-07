package io.github.xesam.geo2district;

import io.github.xesam.geo.Relations;
import io.github.xesam.geo.Point;
import io.github.xesam.geo.Relation;

import java.util.List;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class Geo2district {

    private List<District> districts;
    private Relations relations;

    public Geo2district(DistrictLoader districtLoader) {
        districts = districtLoader.load();
        relations = new Relations();
    }

    public DistrictQuerier getQuerier(String... source) {
        return null;
    }

    public DistrictQuerier getChinaQuerier(String... source) {
        return null;
    }

    @Deprecated
    public Optional<District> toDistrict(Point point) {
        District ret = null;
        for (District district : districts) {
            if (isInDistrict(point, district)) {
                ret = district;
                break;
            }
        }
        if (ret == null) {
            return Optional.empty();
        } else {
            return Optional.of(ret);
        }
    }

    private boolean isInDistrict(Point point, District district) {
        Boundary boundary = district.getBoundary();
        for (List<Point> polygon : boundary.value()) {
            if (relations.getRelation(point, polygon) == Relation.IN) {
                return true;
            }
        }
        return false;
    }
}
