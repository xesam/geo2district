package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class Geo2districtTest {
    static Geo2district geo2district;

    @BeforeClass
    public static void beforeClass() {
        DistrictLoader districtLoader = new FileDistrictLoader("/data/district/geo/skeleton.list.json", "F:\\data.center\\district\\geo");
        geo2district = new Geo2district(districtLoader);
    }

    @Test
    public void toDistrictBeijing() {
        Point point = new Point(116.415017, 39.917192);
        Optional<District> district = geo2district.toDistrict(point);
        Assert.assertEquals(true, district.isPresent());
        Assert.assertEquals("北京市", district.get().getName());
    }

    @Test
    public void toDistrictWuhan() {
        Point point = new Point(114.31, 30.52);
        Optional<District> district = geo2district.toDistrict(point);
        Assert.assertEquals(true, district.isPresent());
        Assert.assertEquals("湖北省", district.get().getName());
    }

    @Test
    public void toDistrictHongkong() {
        Point point = new Point(114.264415, 22.166757);
        Optional<District> district = geo2district.toDistrict(point);
        Assert.assertEquals(true, district.isPresent());
        Assert.assertEquals("香港特别行政区", district.get().getName());
    }

    @Test
    public void toDistrictNothing() {
        Point point = new Point(14.31, 30.52);
        Optional<District> district = geo2district.toDistrict(point);
        Assert.assertEquals(false, district.isPresent());
    }
}
