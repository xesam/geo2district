package io.github.xesam.geo2district;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Geo2district {

    private List<District> districts;

    public Geo2district(DistrictLoader districtLoader) {
        districts = districtLoader.load();
    }

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
        for (List<GeoPoint> boundary : district.getBoundaries()) {
            if (isInBoundary(geoPoint, boundary)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSamePoint(GeoPoint pointA, GeoPoint pointB) {
        return pointA.getLng() == pointB.getLat() && pointA.getLat() == pointB.getLat();
    }

    private boolean isInBoundary(GeoPoint target, List<GeoPoint> boundary) {
        int crossCount = 0;
        if (boundary.size() < 3) {
            return false;
        }
        Iterator<GeoPoint> iterator = boundary.iterator();
        GeoPoint base = iterator.next();
        while (iterator.hasNext()) {
            GeoPoint next = iterator.next();
            if (isSamePoint(target, base) || isSamePoint(target, next)) {
                return false;
            }
            if ((base.getLat() < target.getLat() && next.getLat() >= target.getLat())
                    || (base.getLat() >= target.getLat() && next.getLat() < target.getLat())) {
                double crossLng = next.getLng() - (next.getLat() - base.getLat()) * (next.getLng() - base.getLng()) / (next.getLat() - base.getLat());

                if (crossLng == target.getLng()) {
                    return false;
                }
                if (crossLng < target.getLng()) {
                    crossCount++;
                }
            }

            base = next;
        }
        return crossCount % 2 != 0;
    }
}
