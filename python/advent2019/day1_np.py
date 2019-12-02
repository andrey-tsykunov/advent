
from advent2019.utils import *
import numpy as np

input_file = "day1.txt"


def fuel(mass):
    return mass // 3 - 2


def fuel_rec(mass):
    f = fuel(mass)
    f = f[f > 0]

    return np.sum(f) + np.sum(fuel_rec(f)) if f.size > 0 else 0


if __name__ == "__main__":
    mass = read_np_ints(input_file)

    r1 = np.sum(fuel(mass))
    assert r1 == 3334297

    r2 = fuel_rec(mass)
    print(r2)
    assert r2 == 4998565


