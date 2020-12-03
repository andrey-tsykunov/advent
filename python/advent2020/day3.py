import math
from advent2020.utils import *

input_file = "day3.txt"
map = read_strings(input_file)


def count_trees(a, right=3, down=1):
    width = len(a[0])
    count = 0
    j = 0
    for i in range(0, len(a), down):
        if a[i][j % width] == "#":
            count += 1
        j += right

    return count


def test_task1():

    r = count_trees(map)
    assert r == 270


def test_task2():
    slopes = [
        dict(right=1, down=1),
        dict(right=3, down=1),
        dict(right=5, down=1),
        dict(right=7, down=1),
        dict(right=1, down=2),
    ]
    r = math.prod([count_trees(map, **s) for s in slopes])
    assert r == 2122848000


def test_examples():
    assert count_trees(read_strings("day3_1.txt")) == 7

