package io.github.xesam.geo2district.data;

import io.github.xesam.geo2district.Boundary;

import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public interface BoundarySource {
    Optional<Boundary> load(String adcode);
}
