
from advent2019.utils import *
import numpy as np

input_file = "day1.txt"


def fuel(mass):
    return mass // 3 - 2


def fuel_rec(mass):
    f = fuel(mass)
    f = f[f > 0]

    return np.sum(f) + np.sum(fuel_rec(f)) if f.size > 0 else 0


def fuel_while(mass):
    r = 0

    m = mass
    while np.any(m > 0):
        m = m // 3 - 2
        m[m < 0] = 0
        r += sum(m)

    return r


if __name__ == "__main__":
    mass = read_np_ints(input_file)

    r1 = np.sum(fuel(mass))
    assert r1 == 3334297

    r2 = fuel_rec(mass)
    assert r2 == 4998565

    r3 = fuel_while(mass)
    assert r3 == 4998565


