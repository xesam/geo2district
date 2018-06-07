package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictQuerierTest {
    private static Geo2district geo2district;

    @BeforeClass
    public static void beforeClass() {
        DistrictLoader districtLoader = new FileDistrictLoader("/data/district/geo/skeleton.list.json", "F:\\data.center\\district\\geo");
        geo2district = new Geo2district(districtLoader);
    }

    @Test
    public void toDistrictBeijing() {
        Point point = new Point(116.415017, 39.917192);
        DistrictQuerier querier = geo2district.getChinaQuerier();
        Optional<District> district = querier.query(point);
        Assert.assertTrue(district.isPresent());
        Assert.assertEquals("北京市", district.get().getName());
    }

    @Test
    public void toDistrictNothing() {
        Point point = new Point(14.31, 30.52);
        DistrictQuerier querier = geo2district.getChinaQuerier();
        Optional<District> district = querier.query(point);
        Assert.assertFalse(district.isPresent());
    }
}
