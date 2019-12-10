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


class ProgramState:
    def __init__(self, program, i=0, offset=0, mem=None):
        self.program = program
        self.i = i
        self.offset = offset
        self.mem = mem
        if self.mem is None:
            self.mem = {}

    def get_mem(self, address: int):
        return self.program[address] if address < len(self.program) else self.mem.get(address, 0)

    def set_mem(self, address: int, v: int):
        if address < len(self.program):
            self.program[address] = v
        else:
            self.mem[address] = v


def parse(code: int):
    code_str = str(code)
    modes = [MODE_POS, MODE_POS, MODE_POS]
    if len(code_str) == 1:
        return code, modes
    op = code_str[-2:]
    overrides = [int(c) for c in code_str[-3::-1]]
    modes[0:len(overrides)] = overrides
    return int(op), modes


def resume(state, inputs):

    outputs = []
    input_i = 0
    modes = []

    def get(arg_num: int):
        arg_val = state.i + arg_num
        if modes[arg_num] == MODE_POS:
            address = state.program[arg_val]
        elif modes[arg_num] == MODE_REL:
            address = state.offset + state.program[arg_val]
        elif modes[arg_num] == MODE_VALUE:
            address = arg_val
        else:
            raise ValueError(f"Invalid mode {modes[arg_num]} for arg {arg_num}")

        return state.get_mem(address)

    def set(arg_num: int, v: int):
        arg_val = state.i + arg_num
        if modes[arg_num] == MODE_POS:
            address = state.program[arg_val]
        elif modes[arg_num] == MODE_REL:
            address = state.offset + state.program[arg_val]
        elif modes[arg_num] == MODE_VALUE:
            address = arg_val
        else:
            raise ValueError(f"Invalid mode {modes[arg_num]} for arg {arg_num}")

        state.set_mem(address, v)

    while state.i < len(state.program):
        op, modes = parse(state.program[state.i])
        state.i += 1
        if op == OP_ADD:
            set(2, get(0) + get(1))
            state.i += 3
        elif op == OP_MULTIPLY:
            set(2, get(0) * get(1))
            state.i += 3
        elif op == OP_READ:
            if input_i == len(inputs):
                state.i -= 1
                return outputs, state, EXIT_INPUT

            set(0, inputs[input_i])
            input_i += 1
            state.i += 1
        elif op == OP_WRITE:
            outputs.append(get(0))
            state.i += 1
        elif op == OP_JUMP_IF_TRUE:
            if get(0) != 0:
                state.i = get(1)
            else:
                state.i += 2
        elif op == OP_JUMP_IF_FALSE:
            if get(0) == 0:
                state.i = get(1)
            else:
                state.i += 2
        elif op == OP_LESS_THAN:
            set(2, 1 if get(0) < get(1) else 0)
            state.i += 3
        elif op == OP_EQUALS:
            set(2, 1 if get(0) == get(1) else 0)
            state.i += 3
        elif op == OP_ADJUST_REL_BASE:
            state.offset += get(0)
            state.i += 1
        elif op == OP_HALT:
            break
        else:
            raise ValueError(f"Invalid op {op} for for instruction {state.i}")

    return outputs, state, EXIT_HALT


def run(program, inputs):
    state = ProgramState(program=program.copy())
    outputs, _, exit_code = resume(state, inputs)
    assert exit_code == EXIT_HALT
    return outputs
