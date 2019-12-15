from assertpy import assert_that

from advent2019.day12 import *
from advent2019.utils import *


def parse(name: str):
    return [re.findall(r'(-*\d+)', s) for s in read_strings(name)]


def test_calc_velocity():
    np.testing.assert_array_equal(calc_velocity_delta([-1, 2, 4, 3]), [3, 1, -3, -1])


def test_get_total_energy_1():
    pos = np.array(parse("day12_1.data"), dtype=int)

    assert_that(get_total_energy(pos, 10)).is_equal_to(179)


def test_get_total_energy():
    pos = np.array(parse("day12.data"), dtype=int)

    assert_that(get_total_energy(pos, 1000)).is_equal_to(9127)


def test_steps_to_repeat_1():
    pos = np.array(parse("day12_1.data"), dtype=int)

    assert_that(steps_to_repeat(pos)).is_equal_to(2772)


def test_steps_to_repeat_2():
    pos = np.array(parse("day12_2.data"), dtype=int)

    assert_that(steps_to_repeat(pos, 100000)).is_equal_to(4686774924)


def test_steps_to_repeat():
    pos = np.array(parse("day12.data"), dtype=int)

    assert_that(steps_to_repeat(pos, max_steps=1000000)).is_equal_to(353620566035124)

