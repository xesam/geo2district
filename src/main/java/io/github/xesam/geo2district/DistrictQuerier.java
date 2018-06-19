package io.github.xesam.geo2district;

import io.github.xesam.geo.Point;

import java.util.Optional;

/**
 * TODO:一个让接口更友好的辅助类
 *
 * @author xesamguo@gmail.com
 */
public class DistrictQuerier {

    private DistrictTree districtTree;

    public DistrictQuerier(DistrictTree districtTree) {
        this.districtTree = districtTree;
    }

    public Optional<District> query(String... subNames) {
        Optional<DistrictTree> treeOptional = districtTree.getTreeByName(subNames);
        if (treeOptional.isPresent()) {
            return Optional.ofNullable(treeOptional.get().getDistrict());
        }
        return Optional.empty();
    }

    public Optional<District> query(Point point) {
        Optional<DistrictTree> sub = districtTree.getTreeByPoint(point);
        if (sub.isPresent()) {
            return Optional.ofNullable(sub.get().getDistrict());
        }
        return Optional.empty();
    }
}
