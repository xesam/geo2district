package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
import io.github.xesam.geo.Relation;
import io.github.xesam.geo2district.data.BoundarySource;
import io.github.xesam.geo2district.data.FileBoundarySource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

public class DistrictTest {
    static DistrictSkeleton districtSkeleton;
    static BoundarySource boundarySource;

    @BeforeClass
    public static void beforeClass() {
        File skeletonFile = new File("d:/data.center/district/unified/skeleton.json");
        districtSkeleton = DistrictSkeleton.from(skeletonFile);
        boundarySource = new FileBoundarySource(new File("d:/data.center/district/unified"));
    }

    @Test
    public void toDistrictBeijing() {
        Point point = new Point(116.415017, 39.917192);
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeletonByName("北京市");
        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();
        District district = skeleton.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictWuhan() {
        Point point = new Point(114.31, 30.52);
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeletonByName("湖北省");
        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();
        District district = skeleton.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictHongkong() {
        Point point = new Point(114.264415, 22.166757);
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeletonByName("香港特别行政区");
        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();
        District district = skeleton.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictNothing() {
        Point point = new Point(14.31, 30.52);
        District district = districtSkeleton.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.OUT, relation);
    }

    @Test
    public void toDistrictSkeletonIn() {
        Point point = new Point(116.415017, 39.917192);
        districtSkeleton.inflateBoundaryWithDepth(boundarySource, 0);
        District district = districtSkeleton.getDistrict();
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictSkeletonNothing() {
        Point point = new Point(14.31, 30.52);
        districtSkeleton.inflateBoundaryWithDepth(boundarySource, 0);
        District district = districtSkeleton.getDistrict();
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.OUT, relation);
    }
}
