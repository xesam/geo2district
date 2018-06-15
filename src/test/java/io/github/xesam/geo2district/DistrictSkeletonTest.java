package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;
import io.github.xesam.geo2district.data.BoundarySource;
import io.github.xesam.geo2district.data.FileBoundarySource;
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
    static BoundarySource boundarySource;

    @BeforeClass
    public static void beforeClass() {
        File skeletonFile = new File("d:/data.center/district/unified/skeleton.json");
        districtSkeleton = DistrictSkeleton.from(skeletonFile);
        boundarySource = new FileBoundarySource(new File("d:/data.center/district/unified"));
    }

    @Test
    public void getSubSkeletonByNameWuhan() {
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeletonByName("湖北省", "武汉市");

        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();
        District wuhan = skeleton.getDistrict();

        Assert.assertEquals("420100", wuhan.getAdcode());
        Assert.assertEquals("武汉市", wuhan.getName());
        Assert.assertEquals(114.305469, wuhan.getCenter().getLng(), 0.00001);
        Assert.assertEquals(30.593175, wuhan.getCenter().getLat(), 0.00001);
    }

    @Test
    public void getSubSkeletonByNameWuhanHongshan() {
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeletonByName("湖北省", "武汉市", "洪山区");

        Assert.assertTrue(sub.isPresent());
        DistrictSkeleton skeleton = sub.get();
        District hongshan = skeleton.getDistrict();

        Assert.assertEquals("洪山区", hongshan.getName());
    }

    @Test
    public void getSubSkeletonByNameNotFound() {
        Optional<DistrictSkeleton> sub = districtSkeleton.getSubSkeletonByName("湖北省", "北京市");

        Assert.assertFalse(sub.isPresent());
    }

    @Test
    public void getSubSkeletonByPointInChina() {
        districtSkeleton.inflateBoundaryWithDepth(boundarySource, 2);
        Optional<DistrictSkeleton> wuhanSkeletonOptional = districtSkeleton.getSubSkeletonByPoint(new Point(114.305469, 30.593175));

        Assert.assertTrue(wuhanSkeletonOptional.isPresent());

        DistrictSkeleton skeleton = wuhanSkeletonOptional.get();
        District wuhan = skeleton.getDistrict();

        Assert.assertEquals("武汉市", wuhan.getName());
    }

    @Test
    public void getSubSkeletonByPointInWuhan() {

        Optional<DistrictSkeleton> subOptional = districtSkeleton.getSubSkeletonByName("湖北省", "武汉市");
        DistrictSkeleton sub = subOptional.get();
        sub.inflateBoundaryWithDepth(boundarySource, 0);
        Optional<DistrictSkeleton> wuhanSkeletonOptional = sub.getSubSkeletonByPoint(new Point(114.305469, 30.593175));

        Assert.assertTrue(wuhanSkeletonOptional.isPresent());

        DistrictSkeleton skeleton = wuhanSkeletonOptional.get();
        District wuhan = skeleton.getDistrict();

        Assert.assertEquals("武汉市", wuhan.getName());
    }
}
