package io.github.xesam.geo2district;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.Nullable;
import io.github.xesam.geo.Point;
import io.github.xesam.geo2district.data.GeoSource;
import io.github.xesam.geo2district.data.PointDeserializer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictSkeleton {

    public static DistrictSkeleton from(File skeletonFile) {
        try (FileReader jsonReader = new FileReader(skeletonFile)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Point.class, new PointDeserializer())
                    .registerTypeAdapter(DistrictSkeleton.class, (JsonDeserializer<DistrictSkeleton>) (json, typeOfT, context) -> {
                        District district = context.deserialize(json, District.class);
                        JsonArray jDistricts = json.getAsJsonObject().getAsJsonArray("districts");
                        List<DistrictSkeleton> subSkeletons = context.deserialize(jDistricts, new TypeToken<List<DistrictSkeleton>>() {
                        }.getType());
                        return new DistrictSkeleton(district, subSkeletons);
                    })
                    .create();
            return gson.fromJson(jsonReader, new TypeToken<DistrictSkeleton>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("load skeleton file error");
        }
    }

    private District district;
    private List<DistrictSkeleton> subSkeletons = new ArrayList<>();

    private DistrictSkeleton() {

    }

    public DistrictSkeleton(District district, List<DistrictSkeleton> subSkeletons) {
        this.district = district;
        this.subSkeletons = subSkeletons;
    }

    public District getDistrict() {
        return district;
    }

    public Optional<DistrictSkeleton> getSubSkeleton(String... subNames) {
        DistrictSkeleton current = this;
        for (String name : subNames) {
            current = findKSubSkeleton(current.subSkeletons, name);
            if (current == null) {
                return Optional.empty();
            }
        }
        return Optional.of(current);
    }

    @Nullable
    private DistrictSkeleton findKSubSkeleton(List<DistrictSkeleton> currentSkeletons, String skeletonName) {
        for (DistrictSkeleton skeleton : currentSkeletons) {
            District district = skeleton.getDistrict();
            if (district.getName().equals(skeletonName)) {
                return skeleton;
            }
        }
        return null;
    }

    public void inflateDetailBoundary(GeoSource geoSource) {
        district.inflateBoundary(geoSource);
        for (DistrictSkeleton skeleton : subSkeletons) {
            skeleton.inflateDetailBoundary(geoSource);
        }
    }
}
