package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
import io.github.xesam.geo2district.data.FileGeoSource;

import java.io.File;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictQuerier {
    public static void main(String[] args) {
        File skeletonFile = new File("/data/district/unified/skeleton.json");
        DistrictSkeleton districtSkeleton = DistrictSkeleton.from(skeletonFile);
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeleton("湖北省", "武汉市");
        sub.ifPresent(skeleton -> {
            File dataDir = new File("/data/district/unified/");
            skeleton.inflateBoundary(new FileGeoSource(dataDir));
        });
    }

    private DistrictSkeleton districtSkeleton;

    public DistrictQuerier(DistrictSkeleton districtSkeleton) {
        this.districtSkeleton = districtSkeleton;
    }

    public Optional<District> query(Point point) {
        return Optional.empty();
    }
}
