input = File.read(File.join(__dir__, '../input.txt'))
sections = input.split("\n\n")

ranges = sections[0].lines.map do |line|
  a, b = line.strip.split('-').map(&:to_i)
  a < b ? (a..b) : (b..a)
end

ids = sections[1].to_s.lines.map { |l| l.strip.to_i }

part1 = ids.count do |id|
  ranges.any? { |r| r.include?(id) }
end

puts "Part 1: #{part1}"

sorted = ranges.sort_by(&:begin)
merged = []

sorted.each do |r|
  if merged.empty?
    merged << r
  elsif r.begin <= merged.last.end + 1
    merged[-1] = (merged.last.begin..[merged.last.end, r.end].max)
  else
    merged << r
  end
end

part2 = merged.sum { |r| r.size }

puts "Part 2: #{part2}"