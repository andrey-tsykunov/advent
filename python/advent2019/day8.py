import numpy as np
from functools import reduce
from advent2019.utils import *

data = [int(c) for c in read_strings("day8.txt")[0]]
test = [int(c) for c in "123456789012"]


def min_0_layer():
    image = np.array(data, dtype=int).reshape((-1, 6, 25))
    zero_layers = np.count_nonzero(image == 0, axis=(1,2))
    min_layer = image[np.argmin(zero_layers)]

    return np.count_nonzero(min_layer == 1) * np.count_nonzero(min_layer == 2)


def decode():
    image = np.array(data, dtype=int).reshape((-1, 6, 25))

    def render(x, y):
        r = np.copy(x)
        np.copyto(r, y, where=x == 2)
        return r

    return reduce(render, image)


if __name__ == "__main__":
    assert 2016 == min_0_layer()
    print(decode())
    # HZCZU
