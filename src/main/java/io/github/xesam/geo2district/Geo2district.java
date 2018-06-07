package io.github.xesam.geo2district;

import java.util.List;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class Geo2district {

    private List<District> districts;
    private GeoRelation geoRelation;

    public Geo2district(DistrictLoader districtLoader) {
        districts = districtLoader.load();
        geoRelation = new GeoRelation();
    }

    public DistrictQuerier getQuerier(String... source) {
        return null;
    }

    public DistrictQuerier getChinaQuerier(String... source) {
        return null;
    }

    @Deprecated
    public Optional<District> toDistrict(GeoPoint geoPoint) {
        District ret = null;
        for (District district : districts) {
            if (isInDistrict(geoPoint, district)) {
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

    private boolean isInDistrict(GeoPoint geoPoint, District district) {
        Boundary boundary = district.getBoundary();
        for (List<GeoPoint> polygon : boundary.value()) {
            if (geoRelation.getRelation(geoPoint, polygon) == Relation.IN) {
                return true;
            }
        }
        return false;
    }
}
