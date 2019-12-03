from advent2019.utils import *
from bisect import bisect

codes = read_ints("day2.txt")
apollo = 19690720   # Apollo landing date

OP_ADD = 1
OP_MULTIPLY = 2
OP_HALT = 99


def run(codes, n, v):
    codes = codes.copy()

    codes[1] = n
    codes[2] = v

    i = 0
    while codes[i] != OP_HALT:
        op = codes[i]
        if op == OP_ADD:
            codes[codes[i + 3]] = codes[codes[i + 1]] + codes[codes[i + 2]]
        elif op == OP_MULTIPLY:
            codes[codes[i + 3]] = codes[codes[i + 1]] * codes[codes[i + 2]]
        else:
            raise ValueError(f"Invalid op: ${op}")
        i += 4

    return codes[0]


def task_1():
    assert run(codes, 12, 2) == 5434663


def task_2():
    def search():
        for n in range(100):
            for v in range(100):
                r = run(codes, n, v)
                if r == apollo:
                    return n * 100 + v

    assert 4559 == search()


def task_2_bisect():
    class all:
        def __getitem__(self, item):
            return run(codes, item // 100, item % 100)

    assert 4559 == bisect(all(), apollo, 0, 10000) - 1


def task_1_test():
    assert run([1, 0, 0, 0, 99], 0, 0) == 2
    assert run([2, 3, 0, 3, 99], 3, 0) == 2
    assert run([2, 4, 4, 5, 99, 0], 4, 4) == 2
    assert run([1, 1, 1, 4, 99, 5, 6, 0, 99], 1, 1) == 30


if __name__ == "__main__":
    task_1_test()
    task_1()
    task_2()
    task_2_bisect()
