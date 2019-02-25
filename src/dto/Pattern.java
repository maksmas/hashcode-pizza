package dto;

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
