package entities;

import dto.Pattern;
import dto.Slice;

import java.util.*;
import java.util.stream.Collectors;

public class Pizza {
    private char[][] cells;
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
                if (cells[i][j] == 'T') {
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
                char cell = cells[i][j];

                if (cell == 'T') {
                    this.tCount--;
                } else if (cell == 'M') {
                    this.mCount--;
                } else {
                    throw new RuntimeException("Shouldn't happen");
                }

                cells[i][j] = 'X';
            }
        }
    }

    public Optional<Slice> findBestSlice(Pattern pattern) {
        Optional<Slice> bestSlice = Optional.empty();
        int bestSliceBorderDist = Integer.MAX_VALUE;

        for (int i = 0; i + pattern.getDy() <= rows; ++i) {
            for (int j = 0; j + pattern.getDx() <= cols; ++j) {
                Optional<Slice> slice = getSliceForCoords(j, i, pattern.getDx(), pattern.getDy());

                if (slice.isPresent()) {
                    int sliceXBorderDist = xBorder(slice.get());
                    int sliceYBorderDist = yBorder(slice.get());
                    int totalBorderDist = sliceXBorderDist + sliceYBorderDist;

                    if (!bestSlice.isPresent()) {
                        bestSlice = slice;
                        bestSliceBorderDist = totalBorderDist;
                    } else {
//                        System.out.println("sd: " + totalBorderDist + " ss: " + slice.get().tmpScore);
//                        System.out.println("bsd: " + bestSliceBorderDist + " bss: " + bestSlice.get().tmpScore);

                        if ((bestSlice.get().tmpScore > slice.get().tmpScore) ||
                                (bestSlice.get().tmpScore == slice.get().tmpScore && totalBorderDist < bestSliceBorderDist)
                        ) {
                            bestSlice = slice;
                            bestSliceBorderDist = totalBorderDist;
                        }
                    }
                }
            }
        }

        return bestSlice;
    }

    private int xBorder(Slice slice) {
        int left = slice.x0;
        int right = this.cols - slice.x1;

        return left < right ? left : right;
    }

    private int yBorder(Slice slice) {
        int top = slice.y0;
        int bot = this.rows - slice.y0;

        return top < bot ? top : bot;
    }

    public List<Slice> getCuts() {
        return cuts;
    }

    public int gettCount() {
        return tCount;
    }

    public int getmCount() {
        return mCount;
    }

    private int sliceCoefficient(Slice slice) {
        int tAfterCut = this.tCount - slice.tCount;
        int mAfterCut = this.mCount - slice.mCount;

        return Math.abs(tAfterCut - mAfterCut);
    }

    private Optional<Slice> getSliceForCoords(int x, int y, int dx, int dy) {
        int newTCount = 0;
        int newMCount = 0;

        for (int i = y; i < y + dy; ++i) {
            for (int j = x; j < x + dx; ++j) {
                char cell = cells[i][j];

                if (cell == 'T') {
                    newTCount++;
                } else if (cell == 'M') {
                    newMCount++;
                } else {
                    return Optional.empty();
                }
            }
        }

        if (newMCount < minIngredients || newTCount < minIngredients) {
            return Optional.empty();
        }

        Slice slice = new Slice(x, y, x + dx, y + dy);
        slice.mCount = newMCount;
        slice.tCount = newTCount;
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
