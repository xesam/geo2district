package io.github.xesam.geo2district;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.Nullable;
import io.github.xesam.geo.Point;
import io.github.xesam.geo2district.data.GeoSource;

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

    public static void main(String[] args) {
        File skeletonFile = new File("/data/district/unified/skeleton.json");
        DistrictSkeleton districtSkeleton = DistrictSkeleton.from(skeletonFile);
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeleton("湖北省", "武汉市");
        sub.ifPresent(skeleton -> {
            System.out.println(skeleton.adcode);
            System.out.println(skeleton.name);
            System.out.println(skeleton.center);
        });
    }

    public static DistrictSkeleton from(File skeletonFile) {
        try (FileReader jsonReader = new FileReader(skeletonFile)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Point.class, new PointDeserializer());
            Gson gson = gsonBuilder.create();
            return gson.fromJson(jsonReader, new TypeToken<DistrictSkeleton>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("load skeleton file error");
        }
    }

    @SerializedName("adcode")
    private String adcode = "";
    @SerializedName("name")
    private String name = "";
    @SerializedName("center")
    private Point center;
    @SerializedName("districts")
    private List<DistrictSkeleton> subSkeletons = new ArrayList<>();
    private Boundary boundary = new Boundary();

    public DistrictSkeleton() {

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
            if (skeleton.name.equals(skeletonName)) {
                return skeleton;
            }
        }
        return null;
    }

    public void inflateBoundary(GeoSource geoSource) {
        Optional<Boundary> boundaryOptional = geoSource.load(adcode);
        boundaryOptional.ifPresent(boundary1 -> this.boundary = boundary1);
        for (DistrictSkeleton skeleton : subSkeletons) {
            skeleton.inflateBoundary(geoSource);
        }
    }
}
