package io.github.xesam.geo2district.files;

import com.google.gson.*;
import io.github.xesam.geo2district.core.MultiPolygon;
import io.github.xesam.geo2district.core.Polygon;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
class MultiPolygonDeserializer implements JsonDeserializer<MultiPolygon> {

    @Override
    public MultiPolygon deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        List<Polygon> coordinates = new ArrayList<>();
        JsonArray elements = jsonElement.getAsJsonArray();
        for (JsonElement element : elements) {
            Polygon coordinate = context.deserialize(element, Polygon.class);
            coordinates.add(coordinate);
        }
        return new MultiPolygon(coordinates);
    }
}
