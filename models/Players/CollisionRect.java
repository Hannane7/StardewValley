package com.untildawn.models.Players;

public class CollisionRect {
    private float y, x;
    private float width, height;
    private boolean isGhost;

    public CollisionRect(float x,
                         float y,
                         float width,
                         float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isGhost = false;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith(CollisionRect other) {

        if (isGhost) return false;

        return x < other.x + other.width &&
            x + width > other.x &&
            y < other.y + other.height &&
            y + height > other.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
    public void setGhostMode(boolean ghostMode) {
        this.isGhost = ghostMode;
    }
}
