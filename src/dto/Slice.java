package dto;

public class Slice {
    public int x0;
    public int y0;
    public int x1;
    public int y1;
    public int tCount;
    public int mCount;

    public Slice(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;

        this.tCount = 0;
        this.mCount = 0;
    }

    @Override
    public String  toString() {
        return "Slice{" +
                "x0=" + x0 +
                ", y0=" + y0 +
                ", x1=" + x1 +
                ", y1=" + y1 +
                ", tCount=" + tCount +
                ", mCount=" + mCount +
                '}';
    }
}
