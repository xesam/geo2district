const fs = require("fs");

let municipalities = ["北京市", "上海市", "天津市", "重庆市"];

function is_municipality(district, level) {
    return level === 0 && municipalities.includes(district.name);
}

function flatten_district(district) {
    district.districts = district.districts.reduce(
        (accumulator, currentValue) => {
            return accumulator.concat(currentValue.districts);
        },
        []
    );
    return district;
}

function to_district_list(district, level) {
    let standard = {
        adcode: district.adcode,
        name: district.name,
        level: level,
        center: district.center.split(",").map(item => {
            return parseFloat(item);
        })
    };
    let districts = district.districts;
    if (districts.length > 0) {
        standard.districts = districts.map(district => {
            if (is_municipality(district, level)) {
                district = flatten_district(district);
            }
            return to_district_list(district, level + 1);
        });
    } else {
        standard.districts = [];
    }
    return standard;
}

function to_district_dict(district) {
    let new_district = {};
    new_district.name = district.name;
    new_district.adcode = district.adcode;
    new_district.level = district.level;
    new_district.center = district.center;
    if (!district.districts || district.districts.length == 0) {
        new_district.districts = {};
    } else {
        new_district.districts = district.districts.reduce((acc, curr) => {
            acc[curr.name] = to_district_dict(curr);
            return acc;
        }, {});
    }
    return new_district;
}

function parse_polyline(polyline) {
    return polyline.split("|").map(element => {
        return element.split(";").map(ele => {
            return ele.split(",");
        });
    });
}

function build_skeleton(start_file, list_cbk, dict_cbk) {
    fs.readFile(`${start_file}`, "utf-8", function(err, data) {
        let j_data = JSON.parse(data);
        let countries = j_data.districts;
        let standard_district_list = countries.map(district => {
            return to_district_list(district, 0);
        });

        if (list_cbk) {
            list_cbk(standard_district_list);
        }

        if (dict_cbk) {
            let standard_district_dict = standard_district_list.map(ele => {
                return to_district_dict(ele);
            });
            dict_cbk(standard_district_dict);
        }
    });
}

function load_district(district, in_dir, out_dir) {
    if (district.districts.length > 0) {
        fs.readFile(`${in_dir}/${district.adcode}.json`, "utf-8", function(
            err,
            data
        ) {
            let district = JSON.parse(data).districts[0];
            fs.writeFile(
                `${out_dir}/${district.adcode}.${district.name}.json`,
                JSON.stringify(parse_polyline(district.polyline)),
                "utf-8",
                function(err) {
                    if (err) {
                        console.error(district.adcode, district.name, err);
                    }
                }
            );
        });
        district.districts.forEach(ele => {
            load_district(ele, in_dir, out_dir);
        });
    }
}

function prepare_district(district, in_dir, out_dir) {
    district.districts.forEach(district => {
        load_district(district, in_dir, out_dir);
    });
}

function prepare_districts(districts, in_dir, out_dir) {
    districts.forEach(district => {
        prepare_district(district, in_dir, out_dir);
    });
}

function run(start_file, in_dir, out_dir) {
    build_skeleton(
        start_file,
        lst => {
            let skeleton_file = `${out_dir}/skeleton.list.json`;
            fs.writeFile(skeleton_file, JSON.stringify(lst), "utf-8", function(
                err
            ) {
                if (err) {
                    console.error(err);
                }
            });
            prepare_districts(lst, in_dir, out_dir);
        },
        dict => {
            let skeleton_file = `${out_dir}/skeleton.dict.json`;
            fs.writeFile(skeleton_file, JSON.stringify(dict), "utf-8", function(
                err
            ) {
                if (err) {
                    console.error(err);
                }
            });
        }
    );
}

exports.build_skeleton = build_skeleton;
exports.run = run;
