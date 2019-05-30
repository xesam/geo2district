package io.github.xesam.geo2district;

import java.util.Optional;

public interface DistrictLoader {
    Optional<District> load(String adcode);
}
