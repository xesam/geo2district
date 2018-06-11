package io.github.xesam.geo2district;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.xesam.geo.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@Deprecated
public class FileDistrictLoader implements DistrictLoader {

    private String startFilePath;
    private String districtDirPath;

    public FileDistrictLoader(String startFilePath, String districtDirPath) {
        this.startFilePath = startFilePath;
        this.districtDirPath = districtDirPath;
    }

    @Override
    public List<District> load() {
        List<District> districts = new LinkedList<>();
        Path skeleton = Paths.get(startFilePath);
        try (BufferedReader bfw = Files.newBufferedReader(skeleton)) {
            JSONObject job = JSON.parseObject(bfw.readLine());
            JSONArray dis = job.getJSONArray("districts");
            JSONObject level0 = dis.getJSONObject(0);
            JSONArray level1 = level0.getJSONArray("districts");
            level1.forEach(jsonObject -> {
                JSONObject item = (JSONObject) jsonObject;
                String adCode = item.getString("adcode");
                districts.add(loadDistrict(adCode));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return districts;
    }

    private District loadDistrict(String adCode) {
        District district = new District(adCode);
        Path skeleton = Paths.get(districtDirPath, adCode + ".json");
        try (BufferedReader bfw = Files.newBufferedReader(skeleton)) {
            JSONObject job = JSON.parseObject(bfw.readLine());
            JSONArray dis = job.getJSONArray("districts");
            dis.forEach(jsonObject -> {
                JSONObject item = (JSONObject) jsonObject;
                String name = item.getString("name");
                district.setName(name);
                Boundary boundary = new Boundary();
                String polylines = item.getString("polyline");
                Arrays.asList(polylines.split("\\|")).forEach(polyline -> {
                    List<Point> polygon = new LinkedList<>();
                    Arrays.asList(polyline.split(";")).forEach(point -> {
                        String[] vals = point.split(",");
                        polygon.add(new Point(Double.parseDouble(vals[0]), Double.parseDouble(vals[1])));
                        boundary.add(polygon);
                    });
                });
                district.setBoundary(boundary);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return district;
    }
}
