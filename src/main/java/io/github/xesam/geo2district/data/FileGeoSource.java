package io.github.xesam.geo2district.data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.github.xesam.geo.Point;
import io.github.xesam.geo2district.Boundary;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class FileGeoSource implements GeoSource {

    static class PointDeserializer implements JsonDeserializer<Point> {

        @Override
        public Point deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context)
                throws JsonParseException {
            JsonArray jPoint = jsonElement.getAsJsonArray();
            return new Point(jPoint.get(0).getAsDouble(), jPoint.get(1).getAsDouble());
        }
    }

    private File dataDir;

    public FileGeoSource(File dataDir) {
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
                    .create();
            GeoObj geoObj = gson.fromJson(reader, new TypeToken<GeoObj>() {
            }.getType());
            Optional<MultiPolygon> multiPolygon = geoObj.getGeometry();
            if (multiPolygon.isPresent()) {
                Boundary boundary = new Boundary(multiPolygon.get().getCoordinates());
                return Optional.of(boundary);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
