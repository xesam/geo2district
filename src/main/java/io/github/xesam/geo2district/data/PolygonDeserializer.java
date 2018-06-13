package io.github.xesam.geo2district.data;

import com.google.gson.*;
import io.github.xesam.geo.Point;
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
        List<Point> points = new ArrayList<>();
        JsonArray elements = jsonElement.getAsJsonArray();
        for (JsonElement element : elements) {
            Point point = context.deserialize(element, Point.class);
            points.add(point);
        }
        return new Boundary.Polygon(points);
    }
}
