
from advent2019.utils import *
import numpy as np
input_file = "day1.txt"


def fuel(mass):
    return int(mass / 3) - 2


def fuel_rec(mass):
    f = fuel(mass)

    if f <= 0:
        return 0
    elif f > 0:
        return f + fuel_rec(f)


def total(f):
    return sum(map(f, read_ints(input_file)))


def run_1():
    f1 = total(fuel)
    print(f1)
    assert f1 == 3334297

    f2 = total(fuel_rec)
    print(f2)
    assert f2 == 4998565


def run_2():

    m = read_np_ints(input_file)
    m = np.floor_divide(m, 3) - 2

    assert np.sum(m) == 3334297


if __name__ == "__main__":
    run_2()


