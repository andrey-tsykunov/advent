import re
import numpy as np

from advent2019.utils import *


def calc_velocity_delta(pos_1d):
    r = np.zeros_like(pos_1d)
    for i in range(len(pos_1d)):
        for j in range(len(pos_1d)):
            if pos_1d[i] < pos_1d[j]:
                r[i] += 1
            elif pos_1d[i] > pos_1d[j]:
                r[i] -= 1

    return r


def get_total_energy(pos, steps: int):
    v = np.zeros_like(pos)
    for i in range(steps):
        for c in range(pos.shape[1]):
            v[:,c] += calc_velocity_delta(pos[:, c])
        pos += v

    return np.sum(np.sum(np.abs(pos), axis=1) * np.sum(np.abs(v), axis=1))


def steps_to_repeat(pos):
    initial = np.copy(pos)
    v = np.zeros_like(pos)
    i = 0
    while True:
        for c in range(pos.shape[1]):
            v[:,c] += calc_velocity_delta(pos[:, c])
        pos += v
        # print(f"Position after {i+1}")
        # print(pos)
        # print(f"Velocity after {i+1}")
        # print(v)
        i += 1

        if np.array_equal(pos, initial):
            return i
