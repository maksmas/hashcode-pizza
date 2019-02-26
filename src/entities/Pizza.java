package entities;

import dto.Cell;
import dto.Pattern;
import dto.Slice;

import java.util.*;
import java.util.stream.Collectors;

public class Pizza {
    private Cell[][] cells;
    private int rows;
    private int cols;
    private int tCount = 0;
    private int mCount = 0;

    private List<Slice> cuts = new ArrayList<>();
    private int minIngredients;

    public Pizza(final Input input) {
        this.cells = input.getPizza();
        this.rows = input.getRows();
        this.cols = input.getCols();

        this.minIngredients = input.getMinIngredients();

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

    public void cut(Slice slice) {
        cuts.add(slice);

        for (int i = slice.y0; i < slice.y1; ++i) {
            for (int j = slice.x0; j < slice.x1; ++j) {
                Cell cell = cells[i][j];

                if (cell.getContent() == 'T') {
                    this.tCount--;
                } else if (cell.getContent() == 'M') {
                    this.mCount--;
                } else {
                    throw new RuntimeException("Shouldn't happen");
                }

                cells[i][j].setContent('X');
            }
        }
    }

    public Optional<Slice> findBestSlice(Pattern pattern) {
        Set<Slice> possibleSlices = new HashSet<>();

        for (int i = 0; i  + pattern.getDy() <= rows; ++i) {
            for (int j = 0; j + pattern.getDx() <= cols; ++j) {
                getSliceForCoords(j, i, pattern.getDx(), pattern.getDy()).ifPresent(possibleSlices::add);
            }
        }

        return possibleSlices.stream().reduce((Slice s1, Slice s2) -> s1.tmpScore < s2.tmpScore ? s1 : s2);
    }

    public List<Slice> getCuts() {
        return cuts;
    }

    private int sliceCoefficient(Slice slice) {
        int tAfterCut = this.tCount - slice.tCount;
        int mAfterCut = this.mCount - slice.mCount;

        return Math.abs(tAfterCut - mAfterCut);
    }

    private Optional<Slice> getSliceForCoords(int x, int y, int dx, int dy) {
        Slice slice = new Slice(x, y, x + dx, y + dy);

        for (int i = slice.y0; i < slice.y1; ++i) {
            for (int j = slice.x0; j < slice.x1; ++j) {
                if (cells[i][j].getContent() == 'T') {
                    slice.tCount++;
                } else if (cells[i][j].getContent() == 'M') {
                    slice.mCount++;
                } else {
                    return Optional.empty();
                }
            }
        }

        if (slice.mCount < minIngredients || slice.tCount < minIngredients) {
            return Optional.empty();
        }

        slice.tmpScore = sliceCoefficient(slice);
        return Optional.of(slice);
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
