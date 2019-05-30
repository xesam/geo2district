package io.github.xesam.geo2district.data;

import io.github.xesam.geo2district.District;
import io.github.xesam.geo2district.TestHelper;
import io.github.xesam.geo2district.files.FileDistrictLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class FileDistrictLoaderTest {
    @Test
    public void load() {
        FileDistrictLoader loader = new FileDistrictLoader(TestHelper.getUnifiedDir());
        Optional<District> target = loader.load("130100");
        Assert.assertTrue(target.isPresent());
    }

    @Test
    public void loadNotExist() {
        FileDistrictLoader loader = new FileDistrictLoader(TestHelper.getUnifiedDir());
        Optional<District> target = loader.load("13010990");
        Assert.assertFalse(target.isPresent());
    }
}
