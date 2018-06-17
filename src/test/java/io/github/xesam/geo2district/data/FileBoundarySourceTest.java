package io.github.xesam.geo2district.data;

import io.github.xesam.geo2district.Boundary;
import io.github.xesam.geo2district.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class FileBoundarySourceTest {
    @Test
    public void load() {
        FileBoundarySource fileGeoSource = new FileBoundarySource(TestHelper.getUnifiedDir());
        Optional<Boundary> boundary = fileGeoSource.load("130100");
        Assert.assertTrue(boundary.isPresent());
    }

    @Test
    public void loadNotExist() {
        FileBoundarySource fileGeoSource = new FileBoundarySource(TestHelper.getUnifiedDir());
        Optional<Boundary> boundary = fileGeoSource.load("13010990");
        Assert.assertFalse(boundary.isPresent());
    }
}
