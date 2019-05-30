package io.github.xesam.geo2district.files;

import com.google.gson.*;
import io.github.xesam.geo2district.core.Polygon;
import io.github.xesam.gis.core.Coordinate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
class PolygonDeserializer implements JsonDeserializer<Polygon> {

    @Override
    public Polygon deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        List<Coordinate> coordinates = new ArrayList<>();
        JsonArray elements = jsonElement.getAsJsonArray();
        for (JsonElement element : elements) {
            Coordinate coordinate = context.deserialize(element, Coordinate.class);
            coordinates.add(coordinate);
        }
        return new Polygon(coordinates);
    }
}
