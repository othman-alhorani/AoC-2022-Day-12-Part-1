package Day12_Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class GridBlock {
    int i_index, j_index, height;
    boolean isVisited;

    public GridBlock() {
        isVisited = false;
    }

    public GridBlock(char c) {
        isVisited = false;
        switch (c) {
            case 'S':
                this.height = 0;
                break;
            case 'a':
                this.height = 1;
                break;
            case 'b':
                this.height = 2;
                break;
            case 'c':
                this.height = 3;
                break;
            case 'd':
                this.height = 4;
                break;
            case 'e':
                this.height = 5;
                break;
            case 'f':
                this.height = 6;
                break;
            case 'g':
                this.height = 7;
                break;
            case 'h':
                this.height = 8;
                break;
            case 'i':
                this.height = 9;
                break;
            case 'j':
                this.height = 10;
                break;
            case 'k':
                this.height = 11;
                break;
            case 'l':
                this.height = 12;
                break;
            case 'm':
                this.height = 13;
                break;
            case 'n':
                this.height = 14;
                break;
            case 'o':
                this.height = 15;
                break;
            case 'p':
                this.height = 16;
                break;
            case 'q':
                this.height = 17;
                break;
            case 'r':
                this.height = 18;
                break;
            case 's':
                this.height = 19;
                break;
            case 't':
                this.height = 20;
                break;
            case 'u':
                this.height = 21;
                break;
            case 'v':
                this.height = 22;
                break;
            case 'w':
                this.height = 23;
                break;
            case 'x':
                this.height = 24;
                break;
            case 'y':
                this.height = 25;
                break;
            case 'z':
                this.height = 26;
                break;
            case 'E':
                this.height = 27;
                break;
        }
    }

    public void set_i_j(int i, int j) {
        this.i_index = i;
        this.j_index = j;
    }

    public int get_i() {
        return this.i_index;
    }

    public int get_j() {
        return this.j_index;
    }

    public int getHeight() {
        return this.height;
    }

}

class Grid {
    ArrayList<ArrayList<GridBlock>> grid;
    GridBlock startingPosition, endPosition;
    int steps, nodes_left_in_layer, nodes_in_next_layer;
    boolean reachedEndPosition;

    public Grid() {
        grid = new ArrayList<ArrayList<GridBlock>>();
        steps = 0;
        nodes_left_in_layer = 1;
        nodes_in_next_layer = 0;
        reachedEndPosition = false;
    }

    public int size() {
        return this.grid.size();
    }

    public int getNumOfSteps() {
        return this.steps;
    }

    public void incNumOfSteps() {
        this.steps++;
    }

    public void addRow(ArrayList<GridBlock> row) {
        this.grid.add(row);
    }

    public void setStartingPosition(int i, int j) {
        this.startingPosition = new GridBlock('S');
        this.startingPosition.set_i_j(i, j);
    }

    public void setEndPosition(int i, int j) {
        this.endPosition = new GridBlock('E');
        this.endPosition.set_i_j(i, j);
    }

    public GridBlock getStartingPosition() {
        return this.startingPosition;
    }

    public int getNumberOfColumns() {
        return this.grid.get(0).size();
    }

    public int getNumberOfRows() {
        return this.grid.size();
    }

    public GridBlock getEndPosition() {
        return this.endPosition;
    }

    public GridBlock getBlock(int newI, int newJ) {
        return this.grid.get(newI).get(newJ);
    }

    public void markBlockAsVisited(int i, int j) {
        this.grid.get(i).get(j).isVisited = true;
    }

    public boolean blockIsEndPosition(int i, int j) {
        return this.grid.get(i).get(j).getHeight() == 27;
    }

}

class Day12_part1 {

    public static final int EAST = 0;
    public static final int NORTH = 1;
    public static final int WEST = 2;
    public static final int SOUTH = 3;
    public static final int NUM_OF_DIRECTIONS = 4;

    public static void main(String[] args) {
        try {
            Grid grid = new Grid();
            File inputFile = new File("./assets/day12-input.txt");
            Scanner fileScanner = new Scanner(inputFile);

            readInput(fileScanner, grid);

            int shortestPath = findShortestPath(grid);

            System.out.printf("Shortest path length is: %s \n", shortestPath);

            fileScanner.close();
        } catch (FileNotFoundException error) {
            System.out.println("An error occurred.");
            error.printStackTrace();
        }
    }

    private static int findShortestPath(Grid grid) {
        Queue<Integer> rowQueue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();

        int s_rowIndex = grid.getStartingPosition().get_i();
        int s_colIndex = grid.getStartingPosition().get_j();

        rowQueue.offer(s_rowIndex);
        colQueue.offer(s_colIndex);

        grid.markBlockAsVisited(s_rowIndex, s_colIndex);

        while (rowQueue.size() > 0) {
            int row_index = rowQueue.poll();
            int col_index = colQueue.poll();
            if (grid.blockIsEndPosition(row_index, col_index)) {
                grid.reachedEndPosition = true;
                break;
            }
            explore_neighbours(grid, row_index, col_index, rowQueue, colQueue);
            grid.nodes_left_in_layer--;
            if (grid.nodes_left_in_layer == 0) {
                grid.nodes_left_in_layer = grid.nodes_in_next_layer;
                grid.nodes_in_next_layer = 0;
                grid.incNumOfSteps();
            }
        }
        if (grid.reachedEndPosition) {
            return grid.getNumOfSteps();
        }

        return -1;
    }

    private static void explore_neighbours(Grid grid, int row_index, int col_index, Queue<Integer> rowQueue,
            Queue<Integer> colQueue) {
        int new_row_index = 0, new_col_index = 0;
        for (int i = 0; i < NUM_OF_DIRECTIONS; i++) {
            if (i == EAST) {
                new_row_index = row_index;
                new_col_index = col_index + 1;
            } else if (i == NORTH) {
                new_row_index = row_index - 1;
                new_col_index = col_index;
            } else if (i == WEST) {
                new_row_index = row_index;
                new_col_index = col_index - 1;
            } else if (i == SOUTH) {
                new_row_index = row_index + 1;
                new_col_index = col_index;
            }

            if (new_row_index < 0 || new_col_index < 0) {
                continue;
            }
            if (new_row_index >= grid.getNumberOfRows() || new_col_index >= grid.getNumberOfColumns()) {
                continue;
            }

            if (grid.getBlock(new_row_index, new_col_index).isVisited) {
                continue;
            }
            if (grid.getBlock(new_row_index, new_col_index)
                    .getHeight() > grid.getBlock(row_index, col_index).getHeight() + 1) {
                continue;
            }

            rowQueue.offer(new_row_index);
            colQueue.offer(new_col_index);
            grid.getBlock(new_row_index, new_col_index).isVisited = true;
            grid.nodes_in_next_layer++;
        }
    }

    private static void readInput(Scanner fileScanner, Grid grid) {
        int rowIndex = -1;
        while (fileScanner.hasNextLine()) {
            rowIndex++;
            String str = fileScanner.nextLine();
            char[] charArray = str.toCharArray();
            ArrayList<GridBlock> row = new ArrayList<GridBlock>();
            for (int j = 0; j < charArray.length; j++) {
                GridBlock gridBlock = new GridBlock(charArray[j]);
                gridBlock.set_i_j(rowIndex, j);
                row.add(gridBlock);
                if (charArray[j] == 'S') {
                    grid.setStartingPosition(rowIndex, j);
                } else if (charArray[j] == 'E') {
                    grid.setEndPosition(rowIndex, j);
                }
            }
            grid.addRow(row);
        }
    }

}