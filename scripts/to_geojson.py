import json
import sys
import os
from functools import reduce

MUNICIPALITIES = ["北京市", "上海市", "天津市", "重庆市"]
SPECIAL = ["澳门特别行政区", "香港特别行政区"]


def is_municipality(name):
    return name in MUNICIPALITIES


def is_special(name):
    return name in SPECIAL


def skeleton_load(skeleton_path):
    with open(skeleton_path, encoding='utf-8') as in_file:
        return json.load(in_file)['districts'][0]


def skeleton_extract(raw_skeleton):
    return process_country(raw_skeleton)


def process_country(country):
    provinces = country['districts']
    unified_districts = process_provinces(provinces)
    return {
        'adcode': country['adcode'],
        'name': country['name'],
        'center': get_center(country),
        'districts': unified_districts
    }


def process_provinces(provinces):
    unified = []
    for province in provinces:
        province_name = province['name']
        if is_municipality(province_name):
            unified.append(process_municipality(province))
        elif is_special(province_name):
            unified.append(process_special(province))
        else:
            unified.append(process_province(province))
    return unified


def process_municipality(municipality):
    districts = municipality['districts']
    districts = reduce(lambda total, current: total + current['districts'], districts, [])
    municipality['districts'] = districts
    unified = process_district(municipality)
    return unified


def process_special(special):
    unified = process_district(special)
    return unified


def process_province(province):
    unified = process_district(province)
    return unified


def inspect_district(district):
    print(district['name'], get_center(district))
    pass


def process_district(district):
    unified_districts = []
    if 'districts' in district:
        sub_districts = district['districts']
        for sub_district in sub_districts:
            unified_sub_district = process_district(sub_district)
            unified_districts.append(unified_sub_district)
    else:
        unified_districts.append({})
    return {
        'adcode': district['adcode'],
        'name': district['name'],
        'center': get_center(district),
        'districts': unified_districts
    }


def get_center(district):
    return get_point(district['center'])


def get_point(point_str):
    return [float(x) for x in point_str.split(',')]


def get_polyline(polyline):
    points = polyline.split(';')
    return [get_point(x) for x in points]


def get_polylines(district):
    polylines = district['polyline'].split('|')
    return list(map(get_polyline, polylines))


def district_load(data_dir, adcode):
    with open('{0}/{1}.json'.format(data_dir, adcode), encoding='utf-8') as in_file:
        return json.load(in_file)['districts'][0]


def district_extract(district):
    return {
        "geometry": {
            "type": "MultiPolygon",
            "coordinates": get_polylines(district)
        },
        "properties": {
            "adcode": district['adcode'],
            "name": district['name'],
            "center": get_center(district)
        }
    }


def district_extract_to_file(district, out_dir, override=True):
    adcode = district['adcode']
    out_file_path = '{0}/{1}.json'.format(out_dir, adcode)
    write_flag = override or (not os.path.exists(out_file_path))
    if write_flag:
        extracted = district_extract(district)
        with open('{0}/{1}.json'.format(out_dir, adcode), mode='w', encoding='utf-8') as out_file:
            json.dump(extracted, out_file, indent=4, ensure_ascii=False)


def districts_extract(district_profile, data_dir, out_dir):
    adcode = district_profile['adcode']
    district = district_load(data_dir, adcode)
    district_extract_to_file(district, out_dir, False)
    sub_districts = district_profile['districts']
    for sub_district in sub_districts:
        districts_extract(sub_district, data_dir, out_dir)


def run(argv):
    skeleton_path = argv[1]
    data_dir = argv[2]
    out_dir = argv[3]
    raw_skeleton = skeleton_load(skeleton_path)
    unified_skeleton = skeleton_extract(raw_skeleton)

    with open('{0}/skeleton.json'.format(out_dir), mode='w', encoding='utf-8') as out_file:
        json.dump(unified_skeleton, out_file, indent=4, ensure_ascii=False)

    for district in unified_skeleton:
        districts_extract(district, data_dir, out_dir)


if __name__ == '__main__':
    argv = sys.argv
    run(argv)

# python3 to_geojson.py  /data/raw/skeleton.json /data/raw /data/unified
