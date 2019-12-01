
from advent2019.utils import *

input_file = "day1.txt"


def fuel(mass):
    return int(mass / 3) - 2


def fuel_rec(mass):
    f = fuel(mass)

    return f + fuel_rec(f) if f > 0 else 0


def total(f, mass):
    return sum(map(f, mass))


if __name__ == "__main__":
    mass = read_ints(input_file)

    r1 = total(fuel, mass)
    assert r1 == 3334297

    r2 = total(fuel_rec, mass)
    assert r2 == 4998565


