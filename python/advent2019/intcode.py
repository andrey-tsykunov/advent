from itertools import permutations
from functools import reduce

OP_ADD, OP_MULTIPLY = 1, 2
OP_READ, OP_WRITE = 3, 4
OP_JUMP_IF_TRUE, OP_JUMP_IF_FALSE = 5, 6
OP_LESS_THAN, OP_EQUALS = 7, 8
OP_ADJUST_REL_BASE = 9
OP_HALT = 99
EXIT_HALT, EXIT_INPUT = 0, 1

MODE_POS, MODE_VALUE, MODE_REL = 0, 1, 2


def parse(code: int):
    code_str = str(code)
    modes = [MODE_POS, MODE_POS, MODE_POS]
    if len(code_str) == 1:
        return code, modes
    op = code_str[-2:]
    overrides = [int(c) for c in code_str[-3::-1]]
    modes[0:len(overrides)] = overrides
    return int(op), modes


def resume(codes, inputs, i=0):

    outputs = []
    input_i = 0
    modes = []
    offset = 0
    mem = { }

    def get_mem(address: int):
        return codes[address] if address < len(codes) else mem.get(address, 0)

    def set_mem(address: int, v: int):
        if address < len(codes):
            codes[address] = v
        else:
            mem[address] = v

    def get(di: int):
        get_i = i + di
        address = 0
        if modes[di] == MODE_POS:
            return get_mem(codes[get_i])
        elif modes[di] == MODE_REL:
            return get_mem(offset + codes[get_i])
        elif modes[di] == MODE_VALUE:
            return get_mem(get_i)
        else:
            raise ValueError(f"Invalid mode {modes[di]} for instruction {get_i}")

    def set(di: int, v: int):
        set_i = i + di
        if modes[di] == MODE_POS:
            set_mem(codes[set_i], v)
        elif modes[di] == MODE_REL:
            set_mem(offset + codes[set_i], v)
        elif modes[di] == MODE_VALUE:
            set_mem(set_i, v)
        else:
            raise ValueError(f"Invalid mode {modes[di]} for instruction {set_i}")

    while i < len(codes):
        op, modes = parse(codes[i])
        i += 1
        if op == OP_ADD:
            set(2, get(0) + get(1))
            i += 3
        elif op == OP_MULTIPLY:
            set(2, get(0) * get(1))
            i += 3
        elif op == OP_READ:
            if input_i == len(inputs):
                return outputs, (codes, i - 1), EXIT_INPUT

            set(0, inputs[input_i])
            input_i += 1
            i += 1
        elif op == OP_WRITE:
            outputs.append(get(0))
            i += 1
        elif op == OP_JUMP_IF_TRUE:
            if get(0) != 0:
                i = get(1)
            else:
                i += 2
        elif op == OP_JUMP_IF_FALSE:
            if get(0) == 0:
                i = get(1)
            else:
                i += 2
        elif op == OP_LESS_THAN:
            set(2, 1 if get(0) < get(1) else 0)
            i += 3
        elif op == OP_EQUALS:
            set(2, 1 if get(0) == get(1) else 0)
            i += 3
        elif op == OP_ADJUST_REL_BASE:
            offset += get(0)
            i += 1
        elif op == OP_HALT:
            break
        else:
            raise ValueError(f"Invalid op {op} for for instruction {i}")

    return outputs, (codes, i), EXIT_HALT


def run(codes, inputs):
    codes = codes.copy()
    outputs, _, exit_code = resume(codes, inputs)
    assert exit_code == EXIT_HALT
    return outputs


def run_circuit(codes, inputs):
    print(inputs)
    return reduce(lambda o, i: run(codes, [i, o])[-1], inputs, 0)


def find_max(codes, input_range, f):
    return max(f(codes, p) for p in permutations(input_range))


def find_max_circuit_output(codes, input_range):
    return find_max(codes, input_range, run_circuit)


def run_circuit_with_loop(codes, inputs):
    print(inputs)

    amplifiers = [(codes.copy(), [input], 0) for input in inputs]

    pending_outputs = [0]

    a_i = 0
    while True:
        codes, a_inputs, i = amplifiers[a_i]
        pending_outputs, (updated_codes, updated_i), exit_code = resume(codes, a_inputs + pending_outputs, i)

        if exit_code == EXIT_HALT and a_i == len(amplifiers) - 1:
            return pending_outputs[-1]

        amplifiers[a_i] = (updated_codes, [], updated_i)
        a_i = (a_i + 1) % len(amplifiers)


def find_max_output_with_loop(codes, input_range):
    return find_max(codes, input_range, run_circuit_with_loop)