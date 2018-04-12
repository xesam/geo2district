const fs = require('fs');

let municipalities = ['北京市', '上海市', '天津市', '重庆市'];

function is_municipality(district, level) {
    return level === 0 && municipalities.includes(district.name);
}

function flatten_municipality(district) {
    district.districts = district.districts.reduce((accumulator, currentValue) => {
        return accumulator.concat(currentValue.districts);
    }, []);
    return district;
}

function process_district(district, level) {
    let standard = {
        adcode: district.adcode,
        name: district.name,
        level: level,
        center: district.center.split(',').map(item => { return parseFloat(item) })
    };
    let districts = district.districts;
    if (districts.length > 0) {
        standard.districts = districts.map(district => {
            if (is_municipality(district, level)) {
                flatten_municipality(district);
            }
            return process_district(district, level + 1);
        });
    } else {
        standard.districts = [];
    }
    return standard;
}

function parse_polyline(polyline) {
    return polyline.split('|').map(element => {
        return element.split(';').map(ele => {
            return ele.split(',');
        });
    });
}

function load_district(district) {
    if (district.districts.length > 0) {
        fs.readFile(`./raw/${district.adcode}.json`, 'utf-8', function (err, data) {
            let district = JSON.parse(data).districts[0];
            fs.writeFile(`./geo/${district.adcode}.${district.name}.json`, JSON.stringify(parse_polyline(district.polyline)), 'utf-8', function (err) {
                if (err) {
                    console.error(district.adcode, district.name, err);
                }
            });
        });
        district.districts.forEach(ele => {
            load_district(ele);
        });
    }
}

function transform(input_dir, output_dir, start_file) {
    fs.readFile(`${input_dir}/${start_file}`, 'utf-8', function (err, data) {
        let j_data = JSON.parse(data);
        let countries = j_data.districts;
        let china = countries[0];
        let ret = process_district(china, 0);
        let skeleton_file = `${output_dir}/skeleton.json`;
        fs.writeFile(skeleton_file, JSON.stringify(ret), 'utf-8', function (err) {
            if (err) {
                console.log(err);
            } else {
                fs.readFile(skeleton_file, 'utf-8', function (err, data) {
                    let china = JSON.parse(data);
                    china.districts.forEach(district => {
                        district = load_district(district);
                    });
                });
            }
        });
    });
}

exports.transform = transform;
