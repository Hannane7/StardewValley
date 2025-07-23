package com.untildawn.models.MapElements;

/*
    Every object in out game has a position specified by y and x values.
 */
public class Position {
    private float y;
    private float x;
    public Position(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Position [y=" + y + ", x=" + x + "]";
    }
}
