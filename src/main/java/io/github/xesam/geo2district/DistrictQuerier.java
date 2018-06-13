package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;

import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictQuerier {

    private DistrictSkeleton districtSkeleton;

    public DistrictQuerier(DistrictSkeleton districtSkeleton) {
        this.districtSkeleton = districtSkeleton;
    }

    public Optional<District> query(Point point) {
        return Optional.empty();
    }
}
