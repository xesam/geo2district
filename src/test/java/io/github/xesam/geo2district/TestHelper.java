package io.github.xesam.geo2district;

import io.github.xesam.geo2district.data.BoundarySource;
import io.github.xesam.geo2district.data.FileBoundarySource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestHelper {
    private static Properties prop;

    static {
        prop = new Properties();
        InputStream in = Thread.currentThread().getClass().getResourceAsStream("/test.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getTreeFile() {
        return new File(prop.getProperty("tree_file"));
    }

    public static File getUnifiedDir() {
        return new File(prop.getProperty("unified_dir"));
    }

    public static DistrictTree getDistrictTree() {
        File treeFile = getTreeFile();
        FileDistrictTreeLoader loader = new FileDistrictTreeLoader(treeFile);
        return loader.getDistrictTree();
    }

    public static BoundarySource getBoundarySource() {
        return new FileBoundarySource(getUnifiedDir());
    }
}
