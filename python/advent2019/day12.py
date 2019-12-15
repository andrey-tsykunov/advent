import re
import numpy as np
import logging

from advent2019.utils import *

logger = logging.getLogger(__name__)

def calc_velocity_delta(positions_1d):
    r = np.zeros_like(positions_1d)
    for i in range(len(positions_1d)):
        for j in range(len(positions_1d)):
            if positions_1d[i] < positions_1d[j]:
                r[i] += 1
            elif positions_1d[i] > positions_1d[j]:
                r[i] -= 1

    return r


def get_total_energy(positions_2d, steps: int):
    current = np.copy(positions_2d)
    v = np.zeros_like(current)
    for i in range(steps):
        for c in range(current.shape[1]):
            v[:,c] += calc_velocity_delta(current[:, c])
        current += v

    return np.sum(np.sum(np.abs(current), axis=1) * np.sum(np.abs(v), axis=1))


def find_repeat_seq(positions_seq):
    for x in range(2, int(len(positions_seq) / 2)):
        if np.array_equal(positions_seq[0:x], positions_seq[x:2 * x]):
            return x
    return 0


def steps_to_repeat(positions, max_steps=1000):
    all_pos = np.zeros((max_steps+1,) + positions.shape, dtype=int)
    all_pos[0] = positions
    v = np.zeros_like(positions)
    for i in range(1, max_steps + 1):
        for c in range(positions.shape[1]):
            v[:,c] += calc_velocity_delta(all_pos[i-1, :, c])
        all_pos[i] = all_pos[i-1] + v
        if i % 10000 == 0:
            logger.info(i)

    logger.info("Finding repeats")
    repeats = np.apply_along_axis(find_repeat_seq, axis=0, arr=all_pos)

    if np.any(repeats == 0):
        raise RuntimeError(f"Failed to find repeated sequences for all moon positions. Increase max_steps")

    logger.info("Finding LCM")
    return np.lcm.reduce(repeats.flatten(), dtype=np.int64)


