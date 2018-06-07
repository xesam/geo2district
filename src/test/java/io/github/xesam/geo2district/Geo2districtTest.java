package io.github.xesam.geo2district;

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
        GeoPoint geoPoint = new GeoPoint(116.415017, 39.917192);
        Optional<District> district = geo2district.toDistrict(geoPoint);
        Assert.assertEquals(true, district.isPresent());
        Assert.assertEquals("北京市", district.get().getName());
    }

    @Test
    public void toDistrictWuhan() {
        GeoPoint geoPoint = new GeoPoint(114.31, 30.52);
        Optional<District> district = geo2district.toDistrict(geoPoint);
        Assert.assertEquals(true, district.isPresent());
        Assert.assertEquals("湖北省", district.get().getName());
    }

    @Test
    public void toDistrictHongkong() {
        GeoPoint geoPoint = new GeoPoint(114.264415, 22.166757);
        Optional<District> district = geo2district.toDistrict(geoPoint);
        Assert.assertEquals(true, district.isPresent());
        Assert.assertEquals("香港特别行政区", district.get().getName());
    }

    @Test
    public void toDistrictNothing() {
        GeoPoint geoPoint = new GeoPoint(14.31, 30.52);
        Optional<District> district = geo2district.toDistrict(geoPoint);
        Assert.assertEquals(false, district.isPresent());
    }
}
