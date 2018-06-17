package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
import io.github.xesam.geo.Relation;
import io.github.xesam.geo2district.data.BoundarySource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class DistrictTest {
    static DistrictTree districtTree;
    static BoundarySource boundarySource;

    @BeforeClass
    public static void beforeClass() {
        districtTree = TestHelper.getDistrictTree();
        boundarySource = TestHelper.getBoundarySource();
    }

    @Test
    public void toDistrictBeijing() {
        Point point = new Point(116.415017, 39.917192);
        Optional<DistrictTree> sub = districtTree.getTreeByName("北京市");
        Assert.assertTrue(sub.isPresent());
        DistrictTree skeleton = sub.get();
        District district = skeleton.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictWuhan() {
        Point point = new Point(114.31, 30.52);
        Optional<DistrictTree> sub = districtTree.getTreeByName("湖北省");
        Assert.assertTrue(sub.isPresent());
        DistrictTree skeleton = sub.get();
        District district = skeleton.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictHongkong() {
        Point point = new Point(114.264415, 22.166757);
        Optional<DistrictTree> sub = districtTree.getTreeByName("香港特别行政区");
        Assert.assertTrue(sub.isPresent());
        DistrictTree skeleton = sub.get();
        District district = skeleton.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictNothing() {
        Point point = new Point(14.31, 30.52);
        District district = districtTree.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.OUT, relation);
    }

    @Test
    public void toDistrictSkeletonIn() {
        Point point = new Point(116.415017, 39.917192);
        districtTree.inflateBoundaryWithDepth(boundarySource, 0);
        District district = districtTree.getDistrict();
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictSkeletonNothing() {
        Point point = new Point(14.31, 30.52);
        districtTree.inflateBoundaryWithDepth(boundarySource, 0);
        District district = districtTree.getDistrict();
        Relation relation = district.relationOf(point);
        Assert.assertEquals(Relation.OUT, relation);
    }
}
