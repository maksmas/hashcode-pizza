package entities;

import dto.Cell;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Input {
    private int rows;
    private int cols;
    private int minIngredients;
    private int maxCells;
    private Cell[][] pizza;

    private Input(Builder builder) {
        this.rows = builder.rows;
        this.cols = builder.cols;
        this.minIngredients = builder.minIngredients;
        this.maxCells = builder.maxCells;
        this.pizza = builder.pizza;
    }

    @Override
    public String toString() {
        return "Rows: " + this.rows +
                " Cols: " + this.cols +
                " min. ingredients: " + this.minIngredients +
                " max cells: " + this.maxCells + "\n" +
                Arrays.stream(pizza).map(Arrays::toString).collect(Collectors.joining("\n"));
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMinIngredients() {
        return minIngredients;
    }

    public int getMaxCells() {
        return maxCells;
    }

    public Cell[][] getPizza() {
        return pizza;
    }

    public static class Builder {
        private int rows;
        private int cols;
        private int minIngredients;
        private int maxCells;
        private Cell[][] pizza;
        private int currentRow;

        public Builder(int rows, int cols, int minIngredients, int maxCells) {
            this.currentRow = 0;
            this.rows = rows;
            this.cols = cols;
            this.minIngredients = minIngredients;
            this.maxCells = maxCells;

            pizza = new Cell[rows][];
        }

        public Builder addLine(String line) {
            Cell[] row = new Cell[this.cols];

            for (int i = 0; i < this.cols; ++i) {
                row[i] = new Cell(line.charAt(i), currentRow, i);
            }

            pizza[currentRow] = row;
            ++currentRow;

            return this;
        }

        public Input build() {
            return new Input(this);
        }
    }
}
