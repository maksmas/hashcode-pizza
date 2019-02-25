package dto;

public class Cell {
    private char content;
    private int x;
    private int y;

    public Cell(char content, int row, int coll) {
        this.content = content;
        this.y = row;
        this.x = coll;
    }

    @Override
    public String toString() {
        return String.valueOf(content);
    }

    public char getContent() {
        return content;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
