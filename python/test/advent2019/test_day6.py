from assertpy import assert_that

from advent2019.day6 import *
from advent2019.utils import *

separator = ')'


def test_total_orbits_simple():
    edges = read_strings_2d("day6_1.txt", separator)
    assert_that(total_orbits(edges)).is_equal_to(42)


def test_total_orbits():
    edges = read_strings_2d("day6.txt", separator)
    assert_that(total_orbits(edges)).is_equal_to(241064)


def test_distance_to_santa_simple():
    edges = read_strings_2d("day6_2.txt", separator)
    assert_that(distance_to_santa(edges)).is_equal_to(4)


def test_distance_to_santa():
    edges = read_strings_2d("day6.txt", separator)
    assert_that(distance_to_santa(edges)).is_equal_to(418)
