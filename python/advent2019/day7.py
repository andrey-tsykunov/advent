from advent2019.intcode import *


def run_circuit(codes, inputs):
    print(inputs)
    return reduce(lambda o, i: run(codes, [i, o])[-1], inputs, 0)


def find_max(codes, input_range, f):
    return max(f(codes, p) for p in permutations(input_range))


def find_max_circuit_output(codes, input_range):
    return find_max(codes, input_range, run_circuit)


def run_circuit_with_loop(codes, inputs):
    print(inputs)

    amplifiers = [(ProgramState(codes.copy()), [input]) for input in inputs]

    pending_outputs = [0]

    a_i = 0
    while True:
        state, a_inputs = amplifiers[a_i]
        pending_outputs, state, exit_code = resume(state, a_inputs + pending_outputs)

        if exit_code == EXIT_HALT and a_i == len(amplifiers) - 1:
            return pending_outputs[-1]

        amplifiers[a_i] = (state, [])
        a_i = (a_i + 1) % len(amplifiers)


def find_max_output_with_loop(codes, input_range):
    return find_max(codes, input_range, run_circuit_with_loop)