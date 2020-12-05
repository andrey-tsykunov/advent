from typing import Dict, Optional

import re

from advent2020.utils import *


input_file = "day4.txt"
input = read_string(input_file)

required_fields = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"}
valid_colors = { "amb", "blu", "brn", "gry", "grn", "hzl", "oth"}


def parse(p: str) -> Dict:
    tokens = re.split("[ \n]", p)
    return dict(t.split(":") for t in tokens)


def has_all_fields(p: Dict[str, str]):
    return p.keys() >= required_fields


def is_height_valid(hgt: str):
    x = len(hgt) - 2
    if hgt[x:] == "in":
        return 59 <= int(hgt[:x]) <= 76
    if hgt[x:] == "cm":
        return 150 <= int(hgt[:x]) <= 193
    return False


def is_hair_color_valid(hcl):
    return re.match("^#([0-9a-f]{6}$)", hcl) is not None


def is_eye_color_valid(ecl):
    return ecl in valid_colors


def is_passport_number_valid(pid):
    return re.match("^([0-9]{9}$)", pid) is not None


def is_valid(p: Dict[str, str]):
    return (has_all_fields(p)
        and 1920 <= int(p["byr"]) <= 2002
        and 2010 <= int(p["iyr"]) <= 2020
        and 2020 <= int(p["eyr"]) <= 2030
        and is_height_valid(p["hgt"])
        and is_hair_color_valid(p["hcl"])
        and is_eye_color_valid(p["ecl"])
        and is_passport_number_valid(p["pid"]))


def task1(a: str):
    passports = a.split("\n\n")
    return sum(1 for p in passports if has_all_fields(parse(p)))


def task2(a):
    passports = a.split("\n\n")
    return sum(1 for p in passports if is_valid(parse(p)))


def test_task1():

    r = task1(input)
    assert r == 204


def test_task2():
    r = task2(input)

    assert r == 179


def test_examples_1():
    s = """ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in"""

    assert task1(s) == 2


def test_examples_2():
    assert is_hair_color_valid("#123abc") is True
    assert is_hair_color_valid("#123abz") is False
    assert is_hair_color_valid("123abc") is False


def test_examples_2_all_invalid():
    s = """eyr:1972 cid:100
hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

iyr:2019
hcl:#602927 eyr:1967 hgt:170cm
ecl:grn pid:012533040 byr:1946

hcl:dab227 iyr:2012
ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

hgt:59cm ecl:zzz
eyr:2038 hcl:74454a iyr:2023
pid:3556412378 byr:2007"""

    assert task2(s) == 0


def test_examples_2_all_valid():
    s = """pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
hcl:#623a2f

eyr:2029 ecl:blu cid:129 byr:1989
iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

hcl:#888785
hgt:164cm byr:2001 iyr:2015 cid:88
pid:545766238 ecl:hzl
eyr:2022

iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"""

    assert task2(s) == 4



