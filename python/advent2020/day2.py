
from advent2020.utils import *

input_file = "day2.txt"
input = read_strings(input_file)

def count_valid_passwords(a, is_valid):

    def parse(s: str):
        req, password = s.split(": ")
        times, letter = req.split(" ")
        min, max = times.split("-")

        return int(min), int(max), letter, password

    return sum(1 for s in a if is_valid(*parse(s)))


def min_max_letter(min, max, letter, password: str):
    return min <= password.count(letter) <= max


def only_one_in_position(has, does_not_have, letter, password: str):
    return 1 == (int(password[has - 1] == letter) + int(password[does_not_have - 1] == letter))


def test_task1():
    r = count_valid_passwords(input, min_max_letter)
    assert r == 560


def test_task2():
    r = count_valid_passwords(input, only_one_in_position)
    assert r == 303


def test_examples():

    assert count_valid_passwords(["1-3 a: abcde"], only_one_in_position) == 1
    assert count_valid_passwords(["1-3 b: cdefg"], only_one_in_position) == 0
    assert count_valid_passwords(["2-9 c: ccccccccc"], only_one_in_position) == 0