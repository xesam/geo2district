# geo2district

离线行政区划定位，通过坐标查询行政区划

## 数据来源

高德地图开放接口，然后转换为程序使用的标准格式，格式参见：[格式说明](./doc)

## 使用方式

```java

    // 获取行政区划的树形结构
    DistrictTree districtTree = TestHelper.getDistrictTree();

    //指定边界数据源
    BoundarySource boundarySource = TestHelper.getBoundarySource();

    //加载边界数据，depth = 2 表示只加载到树的第2级
    districtTree.inflateBoundaryWithDepth(boundarySource, 2);

    //根据坐标查找行政区，行政区的级别与加载的 depth 有关
    Optional<DistrictTree> wuhanSkeletonOptional = districtTree.getTreeByPoint(new Point(114.305469, 30.593175));
    DistrictTree wuhanTree = wuhanSkeletonOptional.get();
    District wuhan = wuhanTree.getDistrict();

    Assert.assertEquals("武汉市", wuhan.getName());//true
```

更多参见 test