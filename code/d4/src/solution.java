import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            List<String> grid = new ArrayList<>();
            while (scanner.hasNextLine()) {
                grid.add(scanner.nextLine());
            }
            scanner.close();
            
            System.out.println("Part 1: " + part1(grid));
            System.out.println("Part 2: " + part2(grid));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public static int part1(List<String> grid) {
        int count = 0;
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).length(); j++) {
                if (grid.get(i).charAt(j) == '@') {
                    if (countAdjacentList(grid, i, j) < 4) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int part2(List<String> grid) {
        // Convert to char array for easy modification
        char[][] currentGrid = new char[grid.size()][];
        for (int i = 0; i < grid.size(); i++) {
            currentGrid[i] = grid.get(i).toCharArray();
        }
        
        int totalRemoved = 0;
        
        while (true) {
            List<int[]> toRemove = new ArrayList<>();
            
            // Find all accessible rolls
            for (int i = 0; i < currentGrid.length; i++) {
                for (int j = 0; j < currentGrid[i].length; j++) {
                    if (currentGrid[i][j] == '@') {
                        if (countAdjacent2DArray(currentGrid, i, j) < 4) {
                            toRemove.add(new int[]{i, j});
                        }
                    }
                }
            }
            
            // If no rolls can be removed, we're done
            if (toRemove.isEmpty()) {
                break;
            }
            
            // Remove the accessible rolls
            for (int[] pos : toRemove) {
                currentGrid[pos[0]][pos[1]] = '.';
            }
            
            totalRemoved += toRemove.size();
        }
        
        return totalRemoved;
    }

    // Count adjacent rolls for List<String>
    private static int countAdjacentList(List<String> grid, int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // Skip the center cell itself
                if (i == row && j == col) continue;
                
                // Check bounds
                if (i >= 0 && i < grid.size() && j >= 0 && j < grid.get(i).length()) {
                    if (grid.get(i).charAt(j) == '@') {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Count adjacent rolls for char[][]
    private static int countAdjacent2DArray(char[][] grid, int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // Skip the center cell itself
                if (i == row && j == col) continue;
                
                // Check bounds
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length) {
                    if (grid[i][j] == '@') {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
