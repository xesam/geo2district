package io.github.xesam.geo2district;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictQuerierTest {

    @BeforeClass
    public static void beforeClass() {
    }

    @Test
    public void toDistrictBeijing() {
        Properties prop = new Properties();
        InputStream in = Thread.currentThread().getClass().getResourceAsStream("/test.properties");
        try {
            prop.load(in);
            System.out.println(prop.getProperty("tree_file"));
            System.out.println(prop.getProperty("unified_dir"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toDistrictNothing() {

    }
}
