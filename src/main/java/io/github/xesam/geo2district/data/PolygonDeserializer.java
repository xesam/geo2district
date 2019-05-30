package io.github.xesam.geo2district.data;

import com.google.gson.*;
import io.github.xesam.gis.core.Coordinate;
import io.github.xesam.geo2district.Boundary;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
class PolygonDeserializer implements JsonDeserializer<Boundary.Polygon> {

    @Override
    public Boundary.Polygon deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        List<Coordinate> coordinates = new ArrayList<>();
        JsonArray elements = jsonElement.getAsJsonArray();
        for (JsonElement element : elements) {
            Coordinate coordinate = context.deserialize(element, Coordinate.class);
            coordinates.add(coordinate);
        }
        return new Boundary.Polygon(coordinates);
    }
}
