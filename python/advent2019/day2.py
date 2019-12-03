from advent2019.utils import *
import matplotlib.pyplot as plt

codes = read_ints("day2.txt")


def run_program(codes, n=None, v=None):
    codes = codes.copy()

    if n is not None:
        codes[1] = n
    if v is not None:
        codes[2] = v

    i = 0
    while i < len(codes):
        op = codes[i]
        if op == 1:
            codes[codes[i + 3]] = codes[codes[i + 1]] + codes[codes[i + 2]]
        elif op == 2:
            codes[codes[i + 3]] = codes[codes[i + 1]] * codes[codes[i + 2]]
        elif op == 99:
            break
        i += 4

    return codes


def task_1():
    assert run_program(codes, 12, 2)[0] == 5434663


def task_2():
    def search():
        for n in range(100):
            for v in range(100):
                r = run_program(codes, n, v)[0]
                if r == 19690720:
                    return n * 100 + v

    assert 4559 == search()


def task_2_plot():

    y = []
    for n in range(100):
        for v in range(100):
            y.append(run_program(codes, n, v)[0])

    plt.axhline(y=19690720, color='r', linestyle='-')
    plt.plot(y)
    plt.show()


def task_1_test():
    assert run_program([1, 0, 0, 0, 99]) == [2, 0, 0, 0, 99]
    assert run_program([2, 3, 0, 3, 99]) == [2, 3, 0, 6, 99]
    assert run_program([2, 4, 4, 5, 99, 0]) == [2, 4, 4, 5, 99, 9801]
    assert run_program([1, 1, 1, 4, 99, 5, 6, 0, 99]) == [30, 1, 1, 4, 2, 5, 6, 0, 99]


if __name__ == "__main__":
    task_1_test()
    task_1()
    task_2()
    task_2_plot()
