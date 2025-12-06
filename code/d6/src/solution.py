#!/usr/bin/env python3

import re
import os
from functools import reduce

def parse_input(filename):
    script_dir = os.path.dirname(os.path.abspath(__file__))
    with open(os.path.join(script_dir, '..', filename), 'r') as f:
        return [line.rstrip('\n') for line in f.readlines()]

def find_tokens(line):
    nums = [(m.start(), m.group(), 'number') for m in re.finditer(r'\d+', line)]
    ops = [(m.start(), m.group(), 'operation') for m in re.finditer(r'[+*]', line)]
    return sorted(nums + ops)

def align_columns(lines):
    all_tokens = [find_tokens(line) for line in lines]
    ops_line = all_tokens[-1]
    columns = []
    used_tokens = [set() for _ in range(len(all_tokens) - 1)]
    
    for col_pos, op_token in [(pos, token) for pos, token, _ in ops_line]:
        column = []
        for line_idx in range(len(all_tokens) - 1):
            candidates = [(pos, token) for pos, token, ttype in all_tokens[line_idx] 
                          if ttype == 'number' and pos not in used_tokens[line_idx]]
            if candidates:
                best_pos, best_token = min(candidates, key=lambda x: abs(x[0] - col_pos))
                column.append(int(best_token))
                used_tokens[line_idx].add(best_pos)
        if column:
            column.append(op_token)
            columns.append(column)
    
    return columns

def parse_vertical_numbers(lines):
    if not lines:
        return []
    
    max_len = max(len(line) for line in lines)
    padded = [line.ljust(max_len) for line in lines]
    num_lines = len(padded) - 1
    problems = []
    col = max_len - 1
    current_numbers, current_op = [], None
    
    while col >= 0:
        is_space = all(padded[i][col] == ' ' for i in range(len(padded)))
        if is_space:
            if current_numbers and current_op:
                problems.append((current_numbers, current_op))
            current_numbers, current_op = [], None
        else:
            digits = ''.join(padded[i][col] for i in range(num_lines) if padded[i][col].isdigit())
            if digits:
                current_numbers.append(int(digits))
            if padded[-1][col] in ['+', '*']:
                current_op = padded[-1][col]
        col -= 1
    
    if current_numbers and current_op:
        problems.append((current_numbers, current_op))
    return problems

solve = lambda nums, op: sum(nums) if op == '+' else reduce(lambda a, b: a * b, nums, 1)

def main():
    lines = parse_input('input.txt')
    columns = align_columns(lines)
    total1 = sum(solve(col[:-1], col[-1]) for col in columns if len(col) > 1)
    problems = parse_vertical_numbers(lines)
    total2 = sum(solve(nums, op) for nums, op in problems)
    print(f"Part 1: {total1}")
    print(f"Part 2: {total2}")

if __name__ == '__main__':
    main()
