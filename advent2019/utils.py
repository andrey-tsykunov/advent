from os import path
from decimal import Decimal
import numpy as np

dir_path = path.dirname(path.realpath(__file__))


def read_strings(file_name):
    file_path = path.join(dir_path, file_name)
    with open(file_path, "r") as f:
        return f.readlines()


def read_ints(name):
    return [int(s) for s in read_strings(name)]


def read_ints_2d(name, split_by=","):
    return [[int(c) for c in s.split(split_by)] for s in read_strings(name)]


def read_floats(name):
    return [float(s) for s in read_strings(name)]


def read_floats_2d(name, split_by=","):
    return [[float(c) for c in s.split(split_by)] for s in read_strings(name)]


def read_decimals(name):
    return [Decimal(s) for s in read_strings(name)]


def read_np_ints(name):
    return np.array(read_ints(name), dtype=np.int)


def read_np_int_2d(name, split_by=","):
    a = [s.split(split_by) for s in read_strings(name)]
    return np.array(a, dtype=np.int)


def read_np_floats(name):
    return np.array(read_floats(name), dtype=np.float32)


def read_np_float_2d(name, split_by=","):
    a = [s.split(split_by) for s in read_strings(name)]
    return np.array(a, dtype=np.float32)


