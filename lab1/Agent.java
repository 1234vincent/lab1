package lab1;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Agent {
    // fields
    private Point location;
    private int energy;
    private String identity;

    // constructor
    public Agent() {
        this.location = new Point(0, 0);
        this.energy = 10;
        this.identity = "A";
    }

    public void setX(int x) {
        this.location.setX(x);
    }

    public int getX() {
        return this.location.getX();
    }

    public int getY() {
        return this.location.getY();
    }

    public void setY(int y) {
        this.location.setY(y);
    }

    public Point getLocation() {
        return this.location;
    }

    public void setLocation(Point newSpot) {
        this.location = newSpot;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getIdentity() {
        return this.identity;
    }

    // Move function to move the agent in a specific direction
    public boolean move(Direction direction, Display d) {
        Point newPoint = new Point(getX(), getY());
        switch (direction) {
            case UP:
                newPoint.setY(getY() + 1);
                break;
            case DOWN:
                newPoint.setY(getY() - 1);
                break;
            case RIGHT:
                newPoint.setX(getX() + 1);
                break;
            case LEFT:
                newPoint.setX(getX() - 1);
                break;
        }
        // Check if the new position is valid and move if it is
        if (!occupied(d, newPoint)) {
            setLocation(newPoint);
            return true;
        }
        return false;
    }

    // DFS to eat all food
    public void dfs(Display d, Set<Point> visited) {
        Point currentLocation = getLocation();
        visited.add(currentLocation);
        
        // Try moving in all 4 directions
        for (Direction direction : Direction.values()) {
            Point newPoint = getNextPoint(currentLocation, direction);
            if (newPoint != null && !visited.contains(newPoint) && move(direction, d) && !occupied(d, newPoint)) {
                dfs(d, visited);
            }
        }
    }

    // Get the next point based on direction
    private Point getNextPoint(Point current, Direction direction) {
        switch (direction) {
            case UP:
                return new Point(current.getX(), current.getY() + 1);
            case DOWN:
                return new Point(current.getX(), current.getY() - 1);
            case RIGHT:
                return new Point(current.getX() + 1, current.getY());
            case LEFT:
                return new Point(current.getX() - 1, current.getY());
            default:
                return null;
        }
    }

    // Check if a point is occupied
    public boolean occupied(Display d, Point p) {
        if (d.getDisplay().containsKey(p)) { // if it's in the display
            // Update agent accordingly: food or obstacle
            switch (d.getDisplay().get(p)) {
				case "A": // It hits another agent
					return true;
                case "F":
                    Food f = new Food();
                    f.giveEnergy(this);
                    // Give it energy
                    // Remove F from hashmap? or keep it?
                    return false; // Agents can occupy the same space as food/take the space
                case "O": // It hits an obstacle
                    // Take energy away
                    Obs o = new Obs();
                    o.takeEnergy(this);
                    return true;
                default: // It didn't hit an agent, an obstacle, or food, so the space was empty, the goal, or the initialState
                    return false; // Because the space wasn't occupied
            }
        } else {
            exit(1);
            return false;
        }
    }

    // Enum for directions
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
