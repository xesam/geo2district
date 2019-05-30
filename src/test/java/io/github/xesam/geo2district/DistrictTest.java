package io.github.xesam.geo2district;

import io.github.xesam.gis.core.Coordinate;
import io.github.xesam.gis.core.Relation;
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
        Coordinate coordinate = new Coordinate(116.415017, 39.917192);
        Optional<DistrictTree> sub = districtTree.getTreeByName("北京市");
        Assert.assertTrue(sub.isPresent());
        DistrictTree tree = sub.get();
        District district = tree.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(coordinate);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictWuhan() {
        Coordinate coordinate = new Coordinate(114.31, 30.52);
        Optional<DistrictTree> sub = districtTree.getTreeByName("湖北省");
        Assert.assertTrue(sub.isPresent());
        DistrictTree tree = sub.get();
        District district = tree.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(coordinate);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictHongkong() {
        Coordinate coordinate = new Coordinate(114.264415, 22.166757);
        Optional<DistrictTree> sub = districtTree.getTreeByName("香港特别行政区");
        Assert.assertTrue(sub.isPresent());
        DistrictTree tree = sub.get();
        District district = tree.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(coordinate);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictNothing() {
        Coordinate coordinate = new Coordinate(14.31, 30.52);
        District district = districtTree.getDistrict();
        district.inflateBoundary(boundarySource);
        Relation relation = district.relationOf(coordinate);
        Assert.assertEquals(Relation.OUT, relation);
    }

    @Test
    public void toDistrictTreeIn() {
        Coordinate coordinate = new Coordinate(116.415017, 39.917192);
        districtTree.inflateBoundaryWithDepth(boundarySource, 0);
        District district = districtTree.getDistrict();
        Relation relation = district.relationOf(coordinate);
        Assert.assertEquals(Relation.IN, relation);
    }

    @Test
    public void toDistrictTreeNothing() {
        Coordinate coordinate = new Coordinate(14.31, 30.52);
        districtTree.inflateBoundaryWithDepth(boundarySource, 0);
        District district = districtTree.getDistrict();
        Relation relation = district.relationOf(coordinate);
        Assert.assertEquals(Relation.OUT, relation);
    }
}
