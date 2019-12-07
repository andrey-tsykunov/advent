

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


def run(codes, input):
    codes = codes.copy()

    def get(i: int, mode: int):
        if mode == MODE_POS:
            return codes[codes[i]]
        elif mode == MODE_VALUE:
            return codes[i]
        else:
            raise ValueError(f"Invalid mode: {mode}")

    def set(i: int, mode: int, v: int):
        if mode == MODE_POS:
            codes[codes[i]] = v
        elif mode == MODE_VALUE:
            codes[i] = v
        else:
            raise ValueError(f"Invalid mode: {mode}")

    outputs = []
    i = 0

    while True:
        op, modes = parse(codes[i])
        i += 1
        if op == OP_ADD:
            v = get(i, modes[0]) + get(i + 1, modes[1])
            set(i + 2, modes[2], v)
            i += 3
        elif op == OP_MULTIPLY:
            v = get(i, modes[0]) * get(i + 1, modes[1])
            set(i + 2, modes[2], v)
            i += 3
        elif op == OP_READ:
            set(i, modes[0], input)
            i += 1
        elif op == OP_WRITE:
            v = get(i, modes[0])
            outputs.append(v)
            i += 1
        elif op == OP_JUMP_IF_TRUE:
            if get(i, modes[0]) != 0:
                i = get(i + 1, modes[1])
            else:
                i += 2
        elif op == OP_JUMP_IF_FALSE:
            if get(i, modes[0]) == 0:
                i = get(i + 1, modes[1])
            else:
                i += 2
        elif op == OP_LESS_THAN:
            v = 1 if get(i, modes[0]) < get(i + 1, modes[1]) else 0
            set(i + 2, modes[2], v)
            i += 3
        elif op == OP_EQUALS:
            v = 1 if get(i, modes[0]) == get(i + 1, modes[1]) else 0
            set(i + 2, modes[2], v)
            i += 3
        elif op == OP_HALT:
            break
        else:
            raise ValueError(f"Invalid op: {op}")

    return outputs