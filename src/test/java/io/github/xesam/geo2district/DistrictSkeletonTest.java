package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
import io.github.xesam.geo.Relation;
import io.github.xesam.geo2district.data.FileGeoSource;
import io.github.xesam.geo2district.data.GeoSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictSkeletonTest {
    static DistrictSkeleton districtSkeleton;
    static GeoSource geoSource;

    @BeforeClass
    public static void beforeClass() {
        File skeletonFile = new File("/data/district/unified/skeleton.json");
        districtSkeleton = DistrictSkeleton.from(skeletonFile);
        geoSource = new FileGeoSource(new File("/data/district/unified"));
    }

    @Test
    public void getSubSkeleton() {
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeleton("湖北省", "武汉市");

        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();

        Assert.assertEquals("420100", skeleton.getAdcode());
        Assert.assertEquals("武汉市", skeleton.getName());
        Assert.assertEquals(114.305469, skeleton.getCenter().getLng(), 0.00001);
        Assert.assertEquals(30.593175, skeleton.getCenter().getLat(), 0.00001);
    }

    @Test
    public void toDistrictBeijing() {
        Point point = new Point(116.415017, 39.917192);
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeleton("北京市");
        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();
        skeleton.inflateSimpleBoundary(geoSource);
        Relation relation = skeleton.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictWuhan() {
        Point point = new Point(114.31, 30.52);
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeleton("湖北省");
        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();
        skeleton.inflateSimpleBoundary(geoSource);
        Relation relation = skeleton.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictHongkong() {
        Point point = new Point(114.264415, 22.166757);
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeleton("香港特别行政区");
        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();
        skeleton.inflateSimpleBoundary(geoSource);
        Relation relation = skeleton.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictNothing() {
        Point point = new Point(14.31, 30.52);
        Relation relation = districtSkeleton.relationOf(point);
        districtSkeleton.inflateSimpleBoundary(geoSource);
        Assert.assertEquals(Relation.OUT, relation);
    }
}
