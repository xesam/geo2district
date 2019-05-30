package io.github.xesam.geo2district;

import io.github.xesam.gis.core.Coordinate;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictQuerierTest {

    static DistrictTree districtTree;
    static DistrictLoader districtLoader;

    @BeforeClass
    public static void beforeClass() {
        districtTree = TestHelper.getDistrictTree();
        districtLoader = TestHelper.getDistrictLoader();
    }

    @Test
    public void getTreeByNameWuhan() {
        DistrictQuerier districtQuerier = new DistrictQuerier(districtTree);
        District wuhan = districtQuerier.query("湖北省", "武汉市").get();

        Assert.assertEquals("武汉市", wuhan.getName());
    }

    @Test
    public void getTreeByNameWuhanHongshan() {
        DistrictQuerier districtQuerier = new DistrictQuerier(districtTree);
        District hongshan = districtQuerier.query("湖北省", "武汉市", "洪山区").get();

        Assert.assertEquals("洪山区", hongshan.getName());
    }

    @Test
    public void getTreeByNameNotFound() {
        Optional<DistrictTree> sub = districtTree.getTreeByName("湖北省", "北京市");

        Assert.assertFalse(sub.isPresent());
    }

    @Test
    public void getWuhanTreeByPointInChina() {
        districtTree.inflateForDepth(districtLoader, 2);
        DistrictQuerier districtQuerier = new DistrictQuerier(districtTree);
        District wuhan = districtQuerier.query(new Coordinate(114.305469, 30.593175)).get();

        Assert.assertEquals("武汉市", wuhan.getName());
    }

    @Test
    public void getWuhanTreeByPointInWuhan() {

        Optional<DistrictTree> subOptional = districtTree.getTreeByName("湖北省", "武汉市");
        DistrictTree sub = subOptional.get();
        sub.inflateForDepth(districtLoader, 0);

        DistrictQuerier districtQuerier = new DistrictQuerier(sub);
        District wuhan = districtQuerier.query(new Coordinate(114.305469, 30.593175)).get();

        Assert.assertEquals("武汉市", wuhan.getName());
    }

    @Test
    public void getHongshanTreeByPointInWuhan() {

        Optional<DistrictTree> subOptional = districtTree.getTreeByName("湖北省", "武汉市");
        DistrictTree sub = subOptional.get();
        sub.inflateForDepth(districtLoader, 1);

        DistrictQuerier districtQuerier = new DistrictQuerier(sub);
        //华中科技大学 114.40776, 30.51415
        District hongshan = districtQuerier.query(new Coordinate(114.40776, 30.51415)).get();

        Assert.assertEquals("洪山区", hongshan.getName());
    }
}
