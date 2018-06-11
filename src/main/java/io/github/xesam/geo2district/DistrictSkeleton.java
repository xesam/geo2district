package io.github.xesam.geo2district;

import io.github.xesam.geo2district.data.GeoSource;

import java.io.File;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictSkeleton {

    public DistrictSkeleton(File skeletonFile) {

    }

    public Optional<DistrictSkeleton> getSkeleton(String... subs) {
        return Optional.empty();
    }

    public Optional<DistrictGeo> loadGeoFrom(GeoSource geoSource) {
        return Optional.empty();
    }
}
