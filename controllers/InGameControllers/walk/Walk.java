package com.untildawn.controllers.InGameControllers.walk;

import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Enums.MapConsts.MapSizes;
import com.untildawn.models.App;
import com.untildawn.models.MapElements.GameMap;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;

import java.util.*;

public class Walk {
    public static boolean isWalkable(Tile tile) {
        ItemType tileItemType = tile.getItem().getDefinition().getType();
        return ((tileItemType == ItemType.floor) || (tileItemType == ItemType.fiber)
                || (tileItemType == ItemType.foraging_seeds) || (tileItemType == ItemType.foraging_crops)
                || (tileItemType == ItemType.home) || (tileItemType == ItemType.shop)
                || (tileItemType == ItemType.greenhouse) || (tileItemType == ItemType.quarry)
                || (tileItemType == ItemType.cottage));
    }

    public static int heuristic(Tile a, Tile b) {
        int aX = (int) a.getPosition().getX();
        int bX = (int) b.getPosition().getX();
        int aY = (int) a.getPosition().getY();
        int bY = (int) b.getPosition().getY();

        return Math.abs(aY - bY) + Math.abs(aX - bX);
    }


    public static List<Tile> getNeighbors(Tile tile, GameMap map) {
        List<Tile> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] d : directions) {
            int nY = (int) tile.getPosition().getY() + d[0];
            int nX = (int) tile.getPosition().getX() + d[1];

            if (nY >= 0 && nY < MapSizes.MAP_ROWS.getSize() && nX >= 0 && nX < MapSizes.MAP_COLS.getSize()) {
                Tile neighbor = map.getTile(nY, nX);
                if (isWalkable((neighbor))) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    public static List<Tile> findPath(Tile start, Tile goal, GameMap map) {
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        Set<Tile> closed = new HashSet<>();

        Node startNode = new Node(start, null, 0, 0, heuristic(start, goal), Direction.NONE);
        open.add(startNode);

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.tile.equals(goal)) {
                List<Tile> path = new ArrayList<>();
                while (current != null) {
                    path.add(0, current.tile);
                    current = current.parent;
                }
                return path;
            }

            closed.add(current.tile);

            for (Tile neighbor : getNeighbors(current.tile, map)) {
                if (closed.contains(neighbor)) continue;

                Direction newDir = getDirection(current.tile.getPosition(), neighbor.getPosition());
                int extraTurn = (current.direction != Direction.NONE && current.direction != newDir) ? 1 : 0;

                int newTileSteps = current.tileSteps + 1;
                int newTurns = current.turns + extraTurn;
                int h = heuristic(neighbor, goal);

                Node neighborNode = new Node(neighbor, current, newTileSteps, newTurns, h, newDir);

                boolean betterPath = true;
                for (Node n : open) {
                    if (n.tile.equals(neighbor) && n.getF() <= neighborNode.getF()) {
                        betterPath = false;
                        break;
                    }
                }

                if (betterPath) {
                    open.add(neighborNode);
                }
            }
        }

        return null;
    }


    public static int calculateWalkEnergyCost(List<Tile> path) {
        int numberOfTurns = 0;
        int tileCost = path.size();

        for (int i = 0; i < path.size() - 2; i++) {
            Tile a = path.get(i);
            Tile b = path.get(i + 1);
            Tile c = path.get(i + 2);
            if (!((a.getPosition().getY() == b.getPosition().getY() && b.getPosition().getY() == c.getPosition().getY())
                || (a.getPosition().getX() == b.getPosition().getX() && b.getPosition().getX() == c.getPosition().getX()))) {
                numberOfTurns++;
            }
        }

        return (tileCost + 10 * numberOfTurns) / 20;
    }

    public static void walkToDestination(int energyCost, int goalY, int goalX) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        currentPlayer.setPosition(new Position(goalY, goalX));
        currentPlayer.decreaseEnergy(energyCost);
    }

    public static Position walkUntilEnergyRunsOut(List<Tile> path) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        for (Tile tile : path) {
            currentPlayer.setPosition(tile.getPosition());
            currentPlayer.decreaseEnergy(1);
            if (currentPlayer.getEnergy() == 0) {
                currentPlayer.setFainted(true);
                return currentPlayer.getPosition();
            }
        }

        return currentPlayer.getPosition();
    }

    private static Direction getDirection(Position from, Position to) {
        int dx = (int) (to.getX() - from.getX());
        int dy = (int) (to.getY() - from.getY());

        if (dx == 1) return Direction.RIGHT;
        if (dx == -1) return Direction.LEFT;
        if (dy == 1) return Direction.DOWN;
        if (dy == -1) return Direction.UP;
        return Direction.NONE;
    }
}

