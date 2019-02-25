package entities;

import dto.Cell;
import dto.Pattern;
import dto.Slice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Pizza {
    private Cell[][] cells;
    private int rows;
    private int cols;
    private int tCount = 0;
    private int mCount = 0;

    public Pizza(final Input input) {
        this.cells = input.getPizza();
        this.rows = input.getRows();
        this.cols = input.getCols();

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (cells[i][j].getContent() == 'T') {
                    ++tCount;
                } else {
                    ++mCount;
                }
            }
        }
    }

    public Slice findBestSlice(Pattern pattern) {
        Set<Slice> possibleSlices = new HashSet<>();

        for (int i = 0; i  + pattern.getDy() <= rows; ++i) {
            for (int j = 0; j + pattern.getDx() <= cols; ++j) {
                possibleSlices.add(getSliceForCoords(j, i, pattern.getDx(), pattern.getDy()));
            }
        }

        System.out.println(possibleSlices);
        return possibleSlices.stream().findFirst().get();
    }

    private Slice getSliceForCoords(int x, int y, int dx, int dy) {
        Slice slice = new Slice(x, y, x + dx, y + dy);

        for (int i = slice.y0; i < slice.y1; ++i) {
            for (int j = slice.x0; j < slice.x1; ++j) {
                if (cells[i][j].getContent() == 'T') {
                    slice.tCount++;
                } else if (cells[i][j].getContent() == 'M') {
                    slice.mCount++;
                }
            }
        }

        return slice;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                " rows=" + rows +
                ", cols=" + cols +
                ", tCount=" + tCount +
                ", mCount=" + mCount + "\n" +
                Arrays.stream(cells).map(Arrays::toString).collect(Collectors.joining("\n"));
    }
}
