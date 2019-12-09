from assertpy import assert_that

from advent2019.intcode import *


def parse_data(data: str):
    return [int(c) for c in data.split(',')]

day5_codes = parse_data("3,225,1,225,6,6,1100,1,238,225,104,0,1101,91,67,225,1102,67,36,225,1102,21,90,225,2,13,48,224,101,-819,224,224,4,224,1002,223,8,223,101,7,224,224,1,223,224,223,1101,62,9,225,1,139,22,224,101,-166,224,224,4,224,1002,223,8,223,101,3,224,224,1,223,224,223,102,41,195,224,101,-2870,224,224,4,224,1002,223,8,223,101,1,224,224,1,224,223,223,1101,46,60,224,101,-106,224,224,4,224,1002,223,8,223,1001,224,2,224,1,224,223,223,1001,191,32,224,101,-87,224,224,4,224,102,8,223,223,1001,224,1,224,1,223,224,223,1101,76,90,225,1101,15,58,225,1102,45,42,224,101,-1890,224,224,4,224,1002,223,8,223,1001,224,5,224,1,224,223,223,101,62,143,224,101,-77,224,224,4,224,1002,223,8,223,1001,224,4,224,1,224,223,223,1101,55,54,225,1102,70,58,225,1002,17,80,224,101,-5360,224,224,4,224,102,8,223,223,1001,224,3,224,1,223,224,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1008,677,677,224,102,2,223,223,1005,224,329,1001,223,1,223,1108,677,226,224,1002,223,2,223,1006,224,344,101,1,223,223,107,677,226,224,1002,223,2,223,1006,224,359,101,1,223,223,108,677,677,224,1002,223,2,223,1006,224,374,1001,223,1,223,108,226,677,224,1002,223,2,223,1006,224,389,101,1,223,223,7,226,677,224,102,2,223,223,1006,224,404,1001,223,1,223,1108,677,677,224,1002,223,2,223,1005,224,419,101,1,223,223,1008,226,677,224,102,2,223,223,1006,224,434,101,1,223,223,107,226,226,224,102,2,223,223,1005,224,449,1001,223,1,223,1007,677,677,224,1002,223,2,223,1006,224,464,1001,223,1,223,1007,226,226,224,1002,223,2,223,1005,224,479,101,1,223,223,1008,226,226,224,102,2,223,223,1006,224,494,1001,223,1,223,8,226,226,224,102,2,223,223,1006,224,509,101,1,223,223,1107,677,677,224,102,2,223,223,1005,224,524,1001,223,1,223,1108,226,677,224,1002,223,2,223,1006,224,539,101,1,223,223,1107,677,226,224,1002,223,2,223,1006,224,554,101,1,223,223,1007,677,226,224,1002,223,2,223,1005,224,569,101,1,223,223,7,677,226,224,1002,223,2,223,1006,224,584,101,1,223,223,107,677,677,224,1002,223,2,223,1005,224,599,1001,223,1,223,8,226,677,224,1002,223,2,223,1005,224,614,101,1,223,223,7,677,677,224,1002,223,2,223,1006,224,629,1001,223,1,223,1107,226,677,224,1002,223,2,223,1006,224,644,101,1,223,223,108,226,226,224,102,2,223,223,1005,224,659,1001,223,1,223,8,677,226,224,1002,223,2,223,1005,224,674,101,1,223,223,4,223,99,226")
day7_codes = parse_data("3,8,1001,8,10,8,105,1,0,0,21,42,55,76,89,114,195,276,357,438,99999,3,9,1001,9,3,9,1002,9,3,9,1001,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,101,5,9,9,4,9,99,3,9,102,3,9,9,101,5,9,9,1002,9,2,9,101,4,9,9,4,9,99,3,9,102,5,9,9,1001,9,3,9,4,9,99,3,9,1001,9,4,9,102,5,9,9,1001,9,5,9,1002,9,2,9,101,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99")
day9_codes = parse_data("1102,34463338,34463338,63,1007,63,34463338,63,1005,63,53,1102,3,1,1000,109,988,209,12,9,1000,209,6,209,3,203,0,1008,1000,1,63,1005,63,65,1008,1000,2,63,1005,63,904,1008,1000,0,63,1005,63,58,4,25,104,0,99,4,0,104,0,99,4,17,104,0,99,0,0,1102,521,1,1028,1101,0,36,1000,1102,30,1,1005,1101,21,0,1013,1101,26,0,1006,1102,31,1,1017,1101,24,0,1007,1101,0,1,1021,1102,27,1,1019,1101,23,0,1010,1101,0,38,1012,1102,35,1,1001,1101,25,0,1003,1102,20,1,1004,1101,0,37,1009,1101,424,0,1023,1102,39,1,1008,1102,406,1,1027,1102,1,413,1026,1101,0,29,1002,1102,1,0,1020,1102,34,1,1014,1102,1,28,1018,1102,1,33,1011,1102,300,1,1025,1102,1,22,1015,1102,305,1,1024,1101,32,0,1016,1102,427,1,1022,1101,512,0,1029,109,14,1205,6,197,1001,64,1,64,1106,0,199,4,187,1002,64,2,64,109,-18,1207,8,19,63,1005,63,215,1105,1,221,4,205,1001,64,1,64,1002,64,2,64,109,10,1208,-1,28,63,1005,63,237,1106,0,243,4,227,1001,64,1,64,1002,64,2,64,109,-2,2102,1,0,63,1008,63,22,63,1005,63,263,1105,1,269,4,249,1001,64,1,64,1002,64,2,64,109,11,21107,40,39,0,1005,1015,289,1001,64,1,64,1106,0,291,4,275,1002,64,2,64,109,9,2105,1,0,4,297,1105,1,309,1001,64,1,64,1002,64,2,64,109,-13,2101,0,-5,63,1008,63,25,63,1005,63,329,1105,1,335,4,315,1001,64,1,64,1002,64,2,64,109,1,1206,8,353,4,341,1001,64,1,64,1105,1,353,1002,64,2,64,109,3,2108,37,-6,63,1005,63,375,4,359,1001,64,1,64,1106,0,375,1002,64,2,64,109,-16,1207,2,36,63,1005,63,397,4,381,1001,64,1,64,1105,1,397,1002,64,2,64,109,28,2106,0,0,1001,64,1,64,1106,0,415,4,403,1002,64,2,64,109,-3,2105,1,-1,1106,0,433,4,421,1001,64,1,64,1002,64,2,64,109,-12,2108,25,-6,63,1005,63,449,1105,1,455,4,439,1001,64,1,64,1002,64,2,64,109,-19,1202,8,1,63,1008,63,38,63,1005,63,479,1001,64,1,64,1105,1,481,4,461,1002,64,2,64,109,14,2107,25,0,63,1005,63,497,1105,1,503,4,487,1001,64,1,64,1002,64,2,64,109,24,2106,0,-3,4,509,1001,64,1,64,1105,1,521,1002,64,2,64,109,-20,1208,-2,37,63,1005,63,543,4,527,1001,64,1,64,1106,0,543,1002,64,2,64,109,7,21102,41,1,0,1008,1018,43,63,1005,63,563,1105,1,569,4,549,1001,64,1,64,1002,64,2,64,109,-7,1205,10,587,4,575,1001,64,1,64,1106,0,587,1002,64,2,64,109,-11,1202,5,1,63,1008,63,30,63,1005,63,609,4,593,1106,0,613,1001,64,1,64,1002,64,2,64,109,4,1201,5,0,63,1008,63,34,63,1005,63,637,1001,64,1,64,1105,1,639,4,619,1002,64,2,64,109,12,1206,5,651,1105,1,657,4,645,1001,64,1,64,1002,64,2,64,109,9,21101,42,0,-7,1008,1018,39,63,1005,63,677,1105,1,683,4,663,1001,64,1,64,1002,64,2,64,109,-2,21101,43,0,-8,1008,1015,43,63,1005,63,705,4,689,1106,0,709,1001,64,1,64,1002,64,2,64,109,-25,2107,38,10,63,1005,63,727,4,715,1106,0,731,1001,64,1,64,1002,64,2,64,109,7,2102,1,2,63,1008,63,24,63,1005,63,757,4,737,1001,64,1,64,1105,1,757,1002,64,2,64,109,-13,1201,10,0,63,1008,63,29,63,1005,63,779,4,763,1105,1,783,1001,64,1,64,1002,64,2,64,109,30,21108,44,41,-3,1005,1019,803,1001,64,1,64,1106,0,805,4,789,1002,64,2,64,109,-2,21102,45,1,-7,1008,1013,45,63,1005,63,827,4,811,1105,1,831,1001,64,1,64,1002,64,2,64,109,-16,21107,46,47,7,1005,1011,849,4,837,1106,0,853,1001,64,1,64,1002,64,2,64,109,9,21108,47,47,0,1005,1013,875,4,859,1001,64,1,64,1106,0,875,1002,64,2,64,109,-10,2101,0,2,63,1008,63,30,63,1005,63,901,4,881,1001,64,1,64,1105,1,901,4,64,99,21102,1,27,1,21102,1,915,0,1106,0,922,21201,1,51805,1,204,1,99,109,3,1207,-2,3,63,1005,63,964,21201,-2,-1,1,21101,942,0,0,1106,0,922,22101,0,1,-1,21201,-2,-3,1,21101,0,957,0,1105,1,922,22201,1,-1,-2,1105,1,968,21201,-2,0,-2,109,-3,2105,1,0")


def test_parse():
    assert_that(parse(1)).is_equal_to((1, [MODE_POS, MODE_POS, MODE_POS]))
    assert_that(parse(1002)).is_equal_to((2, [MODE_POS, MODE_VALUE, MODE_POS]))
    assert_that(parse(11002)).is_equal_to((2, [MODE_POS, MODE_VALUE, MODE_VALUE]))


def test_day5_part_1():
    assert_that(run(day5_codes, [1])[-1]).is_equal_to(15508323)


def test_day5_equals_position():
    codes = parse_data("3,9,8,9,10,9,4,9,99,-1,8")

    assert_that(run(codes, [8])[-1]).is_equal_to(1)
    assert_that(run(codes, [9])[-1]).is_equal_to(0)


def test_day5_less_than_position():
    codes = parse_data("3,9,7,9,10,9,4,9,99,-1,8")

    assert_that(run(codes, [7])[-1]).is_equal_to(1)
    assert_that(run(codes, [8])[-1]).is_equal_to(0)


def test_day5_equals_immediate():
    codes = parse_data("3,3,1108,-1,8,3,4,3,99")

    assert_that(run(codes, [8])[-1]).is_equal_to(1)
    assert_that(run(codes, [9])[-1]).is_equal_to(0)


def test_day5_less_than_immediate():
    codes = parse_data("3,3,1107,-1,8,3,4,3,99")

    assert_that(run(codes, [7])[-1]).is_equal_to(1)
    assert_that(run(codes, [8])[-1]).is_equal_to(0)


def test_day5_part_2_large():
    codes = parse_data("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99")

    assert_that(run(codes, [7])[-1]).is_equal_to(999)
    assert_that(run(codes, [8])[-1]).is_equal_to(1000)
    assert_that(run(codes, [9])[-1]).is_equal_to(1001)


def test_day5_part_2():
    assert_that(run(day5_codes, [5])[-1]).is_equal_to(9006327)


def test_day7_find_max_output():
    assert_that(find_max_circuit_output(day7_codes, range(5))).is_equal_to(20413)


def test_day7_find_max_output_with_loop_simple_1():
    codes = parse_data("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5")

    assert_that(find_max_output_with_loop(codes, range(5, 10))).is_equal_to(139629729)


def test_day7_find_max_output_with_loop_simple_2():
    codes = parse_data("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10")

    assert_that(find_max_output_with_loop(codes, range(5, 10))).is_equal_to(18216)


def test_day7_find_max_output_with_loop():
    assert_that(find_max_output_with_loop(day7_codes, range(5, 10))).is_equal_to(3321777)


def test_day9_copy_of_itself():
    codes = parse_data("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99")

    assert_that(run(codes, [])).is_equal_to(codes)


def test_day9_outputs_16_digits_num():
    codes = parse_data("1102,34915192,34915192,7,4,7,99,0")

    output = run(codes, [])[0]
    assert_that(str(output)).is_length(16)


def test_day9_outputs_large_num():
    codes = parse_data("104,1125899906842624,99")

    assert_that(run(codes, [])).is_equal_to([1125899906842624])


def test_day9_part1():
    assert_that(run(day9_codes, [1])).is_equal_to([2351176124])


def test_day9_part2():
    assert_that(run(day9_codes, [2])).is_equal_to([73110])