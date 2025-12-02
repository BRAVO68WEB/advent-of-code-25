#!/bin/bash

INPUT_FILE="input.txt"

p1() {
    local position=50
    local count=0

    while IFS= read -r line; do
        local direction="${line:0:1}"
        local distance="${line:1}"

        if [[ "$direction" == "L" ]]; then
            position=$(( (position - distance + 10000) % 100 ))
        else
            position=$(( (position + distance) % 100 ))
        fi

        if (( position == 0 )); then
            ((count++))
        fi
    done < "$INPUT_FILE"

    echo "$count"
}

p2() {
    local position=50
    local count=0

    while IFS= read -r line; do
        local direction="${line:0:1}"
        local distance="${line:1}"

        for (( i = 1; i <= distance; i++ )); do
            if [[ "$direction" == "L" ]]; then
                position=$(( (position - 1 + 100) % 100 ))
            else
                position=$(( (position + 1) % 100 ))
            fi

            if (( position == 0 )); then
                ((count++))
            fi
        done
    done < "$INPUT_FILE"

    echo "$count"
}

echo "Part 1: $(p1)"
echo "Part 2: $(p2)"
