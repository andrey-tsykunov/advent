from os import path
from decimal import Decimal

dir_path = path.dirname(path.realpath(__file__))


def read_strings(name):
    file_path = path.join(dir_path, name)
    with open(file_path, "r") as f:
        return f.readlines()


def read_ints(name):
    return list(map(int, read_strings(name)))


def read_floats(name):
    return list(map(float, read_strings(name)))


def read_decimals(name):
    return list(map(Decimal, read_strings(name)))