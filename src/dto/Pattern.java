package dto;

import java.util.Objects;

public class Pattern {
    private int dx;
    private int dy;
    private int size;

    public Pattern(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        this.size = dx * dy;
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "dx=" + dx +
                ", dy=" + dy +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pattern pattern = (Pattern) o;
        return dx == pattern.dx &&
                dy == pattern.dy &&
                size == pattern.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dx, dy, size);
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getSize() {
        return size;
    }
}
