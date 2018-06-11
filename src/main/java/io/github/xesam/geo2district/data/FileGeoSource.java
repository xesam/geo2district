package io.github.xesam.geo2district.data;

import io.github.xesam.geo2district.Boundary;

import java.io.File;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class FileGeoSource implements GeoSource {

    private File dataDir;

    public FileGeoSource(File dataDir) {
        this.dataDir = dataDir;
    }

    @Override
    public Optional<Boundary> load(String adcode) {
        File adcodeFile = new File(dataDir, adcode + ".json");
        return Optional.empty();
    }
}
