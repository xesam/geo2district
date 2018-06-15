package io.github.xesam.geo2district;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.Nullable;
import io.github.xesam.geo.Point;
import io.github.xesam.geo.Relation;
import io.github.xesam.geo2district.data.BoundarySource;
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

    private boolean isPointInDistrict(District district, Point point) {
        Relation relation = district.relationOf(point);
        return relation == Relation.IN;
    }

    public Optional<DistrictSkeleton> getSubSkeletonByPoint(Point point) {
        //todo 这一步判断是否有必要
        if (!isPointInDistrict(district, point)) {
            return Optional.empty();
        }
        return getSubSkeletonByPoint(this, point);
    }

    private Optional<DistrictSkeleton> getSubSkeletonByPoint(DistrictSkeleton districtSkeleton, Point point) {
        List<DistrictSkeleton> subs = districtSkeleton.subSkeletons;
        for (DistrictSkeleton sub : subs) {
            if (isPointInDistrict(sub.district, point)) {
                return getSubSkeletonByPoint(sub, point);
            }
        }
        return Optional.of(districtSkeleton);
    }

    public Optional<DistrictSkeleton> getSubSkeletonByName(String... subNames) {
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

    public void inflateBoundaryWithDepth(BoundarySource boundarySource, int depth) {
        if (district == null) {
            return;
        }
        district.inflateBoundary(boundarySource);
        if (depth <= 0) {
            return;
        }
        for (DistrictSkeleton skeleton : subSkeletons) {
            skeleton.inflateBoundaryWithDepth(boundarySource, depth - 1);
        }
    }

    public void inflateBoundaryAll(BoundarySource boundarySource) {
        inflateBoundaryWithDepth(boundarySource, Integer.MAX_VALUE);
    }

}
