package io.github.xesam.geo2district.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import io.github.xesam.geo2district.District;
import io.github.xesam.geo2district.DistrictTree;
import io.github.xesam.geo2district.DistrictTreeLoader;
import io.github.xesam.gis.core.Coordinate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author xesamguo@gmail.com
 */
public class FileDistrictTreeLoader implements DistrictTreeLoader {


    private File treeFile;

    public FileDistrictTreeLoader(File file) {
        this.treeFile = file;
    }

    @Override
    public DistrictTree getDistrictTree() {
        try (FileReader jsonReader = new FileReader(treeFile)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Coordinate.class, new CoordinateDeserializer())
                    .registerTypeAdapter(DistrictTree.class, (JsonDeserializer<DistrictTree>) (json, typeOfT, context) -> {
                        District district = context.deserialize(json, District.class);
                        JsonArray jDistricts = json.getAsJsonObject().getAsJsonArray("districts");
                        List<DistrictTree> subTrees = context.deserialize(jDistricts, new TypeToken<List<DistrictTree>>() {
                        }.getType());
                        return new DistrictTree(district, subTrees);
                    })
                    .create();
            return gson.fromJson(jsonReader, new TypeToken<DistrictTree>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("load tree file error");
        }
    }
}
