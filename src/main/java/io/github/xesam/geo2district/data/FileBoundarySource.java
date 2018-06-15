package io.github.xesam.geo2district.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.xesam.geo.Point;
import io.github.xesam.geo2district.Boundary;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class FileBoundarySource implements BoundarySource {

    private File dataDir;

    public FileBoundarySource(File dataDir) {
        this.dataDir = dataDir;
    }

    private File getAdCodeFile(String adcode) {
        return new File(dataDir, adcode + ".json");
    }

    @Override
    public Optional<Boundary> load(String adcode) {
        File adcodeFile = getAdCodeFile(adcode);
        try (FileReader reader = new FileReader(adcodeFile)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Point.class, new PointDeserializer())
                    .registerTypeAdapter(Boundary.Polygon.class, new PolygonDeserializer())
                    .create();
            GeoObj geoObj = gson.fromJson(reader, new TypeToken<GeoObj>() {
            }.getType());
            Optional<MultiPolygon> multiPolygonOptional = geoObj.getGeometry();
            if (multiPolygonOptional.isPresent()) {
                MultiPolygon multiPolygon = multiPolygonOptional.get();
                Boundary boundary = new Boundary(multiPolygon.getCoordinates());
                return Optional.of(boundary);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
