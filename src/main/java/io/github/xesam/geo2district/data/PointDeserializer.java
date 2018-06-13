package io.github.xesam.geo2district.data;

import com.google.gson.*;
import io.github.xesam.geo.Point;

import java.lang.reflect.Type;

/**
 * @author xesamguo@gmail.com
 */
class PointDeserializer implements JsonDeserializer<Point> {

    @Override
    public Point deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        JsonArray jPoint = jsonElement.getAsJsonArray();
        return new Point(jPoint.get(0).getAsDouble(), jPoint.get(1).getAsDouble());
    }
}
