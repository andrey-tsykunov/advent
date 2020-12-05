
from advent2020.utils import *

input_file = "day5.txt"
input = read_strings(input_file)

ROWS_COUNT = 128
COLUMNS_COUNT = 8
SETS_COUNT = ROWS_COUNT * COLUMNS_COUNT

def decode_row(s: str):
    bin = s[:7].replace("F", "0").replace("B", "1")
    row = int(bin, 2)
    return row

def decode_column(s: str):
    bin = s[7:].replace("L", "0").replace("R", "1")
    row = int(bin, 2)
    return row

def id(row, column):
    return row * COLUMNS_COUNT + column

def decode_id(s):
    return id(decode_row(s), decode_column(s))

def find_highest_id(a):
    return max(map(decode_id, a))

def find_free_seat(a):

    seats = [[False] * COLUMNS_COUNT for _ in range(ROWS_COUNT)]

    for s in a:
        row = decode_row(s)
        column = decode_column(s)
        seats[row][column] = True

    for r in range(ROWS_COUNT):
        for c in range(COLUMNS_COUNT):
            if not seats[r][c]:

                prev = r * COLUMNS_COUNT + c - 1
                prev_r, prev_c = int(prev / COLUMNS_COUNT), prev % COLUMNS_COUNT

                following = r * COLUMNS_COUNT + c + 1
                following_r, following_c = int(following / COLUMNS_COUNT), following % COLUMNS_COUNT

                if prev >= 0 and seats[prev_r][prev_c] and following <= SETS_COUNT and seats[following_r][following_c]:
                    print(f"row: {r}, column: {c}, id = {id(r, c)}")
                    return id(r, c)


def test_task1():
    r = find_highest_id(input)
    assert r == 806


def test_task2():
    r = find_free_seat(input)
    assert r == 562


def test_examples():
    assert decode_row("BFFFBBFRRR") == 70
    assert decode_column("BFFFBBFRRR") == 7

    assert decode_id("BFFFBBFRRR") == 567
    assert decode_id("FFFBBBFRRR") == 119
    assert decode_id("BBFFBBFRLL") == 820


