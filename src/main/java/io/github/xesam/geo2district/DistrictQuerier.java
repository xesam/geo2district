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

    public Optional<District> query(String... subNames) {
        Optional<DistrictSkeleton> skeleton = districtSkeleton.getSubSkeleton(subNames);
        if (skeleton.isPresent()) {
            return Optional.ofNullable(skeleton.get().getDistrict());
        }
        return Optional.empty();
    }

    public Optional<District> query(Point point) {
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeleton(point);
        if (sub.isPresent()) {
            return Optional.ofNullable(sub.get().getDistrict());
        }
        return Optional.empty();
    }
}
