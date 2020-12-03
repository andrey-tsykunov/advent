
from advent2020.utils import *

input_file = "day1.txt"


def two_sum(a, target):
    seen = set()

    for n in a:
        x = target - n
        if x in seen:
            return x * n
        seen.add(n)


def two_sum_2020(a):
    return two_sum(a, 2020)


def three_sum_2020(a):
    for i, n in enumerate(a):
        r = two_sum(a[i:], 2020 - n)

        if r:
            return r * n



def test_task1():
    a = read_ints(input_file)
    r = two_sum_2020(a)

    assert r == 1006176


def test_task2():
    a = read_ints(input_file)
    r = three_sum_2020(a)

    assert r == 0



