use std::fs;

fn max_in_window(b: &[u8], start: usize, end: usize) -> (u8, usize) {
    let mut maxb = b'0';
    let mut maxi = start;
    for i in start..end {
        if b[i] > maxb {
            maxb = b[i];
            maxi = i;
        }
    }
    (maxb - b'0', maxi)
}

fn max_subseq_number(s: &str, k: usize) -> u128 {
    let b = s.as_bytes();
    let n = b.len();
    if k == 0 || n == 0 {
        return 0;
    }

    let k = k.min(n);
    let mut out : u128 = 0;
    let mut start = 0;

    for remaining in (1..=k).rev() {
        let end = n - remaining + 1;
        let (digit, idx) = max_in_window(b, start, end);
        out = out * 10 + u128::from(digit);
        start = idx + 1;
    }
    out
}

fn main() {
    let input = fs::read_to_string("input.txt").unwrap();
    let lines = input.lines().collect::<Vec<&str>>();
    let part1 = lines.iter().map(|line| max_subseq_number(line, 2)).sum::<u128>();
    let part2 = lines.iter().map(|line| max_subseq_number(line, 12)).sum::<u128>();
    println!("Part 1: {}", part1);
    println!("Part 2: {}", part2);
}