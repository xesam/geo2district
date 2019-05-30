package io.github.xesam.geo2district;

import com.sun.istack.internal.Nullable;
import io.github.xesam.geo.Point;
import io.github.xesam.gis.core.Relation;
import io.github.xesam.geo2district.data.BoundarySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xesamguo@gmail.com
 */
public class DistrictTree {

    private District district;
    private List<DistrictTree> subTrees = new ArrayList<>();

    private DistrictTree() {

    }

    public DistrictTree(District district, List<DistrictTree> subTrees) {
        this.district = district;
        this.subTrees = subTrees;
    }

    public District getDistrict() {
        return district;
    }

    private boolean isPointInDistrict(District district, Point point) {
        Relation relation = district.relationOf(point);
        return relation == Relation.IN;
    }

    public Optional<DistrictTree> getTreeByPoint(Point point) {
        //todo 这一步判断是否有必要
        if (!isPointInDistrict(district, point)) {
            return Optional.empty();
        }
        return getTreeByPoint(this, point);
    }

    private Optional<DistrictTree> getTreeByPoint(DistrictTree districtTree, Point point) {
        List<DistrictTree> subs = districtTree.subTrees;
        for (DistrictTree sub : subs) {
            if (isPointInDistrict(sub.district, point)) {
                return getTreeByPoint(sub, point);
            }
        }
        return Optional.of(districtTree);
    }

    public Optional<DistrictTree> getTreeByName(String... subNames) {
        DistrictTree current = this;
        for (String name : subNames) {
            current = findTreeByName(current.subTrees, name);
            if (current == null) {
                return Optional.empty();
            }
        }
        return Optional.of(current);
    }

    @Nullable
    private DistrictTree findTreeByName(List<DistrictTree> trees, String treeName) {
        for (DistrictTree tree : trees) {
            District district = tree.getDistrict();
            if (district.getName().equals(treeName)) {
                return tree;
            }
        }
        return null;
    }

    public void inflateBoundaryWithDepth(BoundarySource boundarySource, int depth) {
        if (district == null) {
            return;
        }
        district.inflateBoundary(boundarySource);
        if (depth <= 0) {
            return;
        }
        for (DistrictTree tree : subTrees) {
            tree.inflateBoundaryWithDepth(boundarySource, depth - 1);
        }
    }

    public void inflateBoundaryAll(BoundarySource boundarySource) {
        inflateBoundaryWithDepth(boundarySource, Integer.MAX_VALUE);
    }

}
