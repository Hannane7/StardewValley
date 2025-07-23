package com.untildawn.controllers.InGameControllers.walk;

import com.untildawn.models.MapElements.Tile;

public class Node {
    Tile tile;
    Node parent;
    int tileSteps; // number of steps (like before)
    int turns;
    int h;
    Direction direction; // new!

    public Node(Tile tile, Node parent, int tileSteps, int turns, int h, Direction direction) {
        this.tile = tile;
        this.parent = parent;
        this.tileSteps = tileSteps;
        this.turns = turns;
        this.h = h;
        this.direction = direction;
    }

    public double getF() {
        return (tileSteps + 10 * turns + h) / 20.0;
    }
}
