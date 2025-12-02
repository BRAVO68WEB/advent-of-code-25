#include <string.h>
#include <fstream>
#include <iostream>

using namespace std;

bool hasRepeatingPattern(const string& s, int len, int patternLen) {
    if (len % patternLen != 0) return false;
    for (int i = patternLen; i < len; i++)
        if (s[i] != s[i % patternLen]) return false;
    return true;
}

int main() {
    FILE* f = fopen("input.txt", "r");
    char line[1000];
    fgets(line, sizeof(line), f);
    fclose(f);

    long long part1 = 0, part2 = 0;
    int i = 0;

    while (line[i]) {
        long long start = 0, end = 0;
        
        // Parse range: start-end
        while (line[i] >= '0' && line[i] <= '9') start = start * 10 + line[i++] - '0';
        i++; // skip '-'
        while (line[i] >= '0' && line[i] <= '9') end = end * 10 + line[i++] - '0';
        if (line[i] == ',') i++;

        for (long long num = start; num <= end; num++) {
            string s = to_string(num);
            int len = s.length();

            // Part 1: pattern repeated exactly twice
            if (len % 2 == 0 && hasRepeatingPattern(s, len, len / 2)) part1 += num;

            // Part 2: pattern repeated 2+ times
            for (int plen = 1; plen <= len / 2; plen++)
                if (hasRepeatingPattern(s, len, plen)) { part2 += num; break; }
        }
    }

    cout<<"Part 1: " <<part1<<endl;
    cout<<"Part 2: " <<part2<<endl;
    return 0;
}