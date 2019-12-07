from itertools import permutations

OP_ADD, OP_MULTIPLY = 1, 2
OP_READ, OP_WRITE = 3, 4
OP_JUMP_IF_TRUE, OP_JUMP_IF_FALSE = 5, 6
OP_LESS_THAN, OP_EQUALS = 7, 8
OP_HALT = 99

MODE_POS, MODE_VALUE = 0, 1


def parse(code: int):
    code_str = str(code)
    modes = [MODE_POS, MODE_POS, MODE_POS]
    if len(code_str) == 1:
        return code, modes
    op = code_str[-2:]
    overrides = [int(c) for c in code_str[-3::-1]]
    modes[0:len(overrides)] = overrides
    return int(op), modes


def run(codes, inputs):
    codes = codes.copy()

    outputs = []
    i = 0
    input_i = 0
    modes = []

    def get(d: int):
        get_i = i + d
        if modes[d] == MODE_POS:
            return codes[codes[get_i]]
        elif modes[d] == MODE_VALUE:
            return codes[get_i]
        else:
            raise ValueError(f"Invalid mode {modes[d]} for instruction {get_i}")

    def set(d: int, v: int):
        set_i = i + d
        if modes[d] == MODE_POS:
            codes[codes[set_i]] = v
        elif modes[d] == MODE_VALUE:
            codes[set_i] = v
        else:
            raise ValueError(f"Invalid mode {modes[d]} for instruction {set_i}")

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
        elif op == OP_HALT:
            break
        else:
            raise ValueError(f"Invalid op {op} for for instruction {i}")

    return outputs


def run_circuit(codes, inputs):
    print(inputs)
    output = 0
    x2 = 0
    for x1 in inputs:
        output = run(codes, [x1, x2])[-1]
        x2 = output

    return output


def run_circuit_with_loop(codes, inputs):
    print(inputs)
    output = 0
    x2 = 0
    for x1 in inputs:
        output = run(codes, [x1, x2])[-1]
        x2 = output

    return output


def find_max_output(codes, input_range):
    return max(run_circuit(codes, p) for p in permutations(input_range))


def find_max_output_with_loop(codes, input_range):
    return max(run_circuit_with_loop(codes, p) for p in permutations(input_range))