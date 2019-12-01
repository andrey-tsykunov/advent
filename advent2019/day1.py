
from advent2019.utils import *

input_file = "day1.txt"


def task_1(mass):
    return int(mass / 3) - 2


def task_2(mass):
    f = task_1(mass)

    if f <= 0:
        return 0
    elif f > 0:
        return f + task_2(f)


def total(f):
    return sum(map(f, read_ints(input_file)))


def run_1():
    f1 = total(task_1)
    print(f1)
    assert f1 == 3334297

    f2 = total(task_2)
    print(f2)
    assert f2 == 4998565


if __name__ == "__main__":
    run_1()


