package lab1;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Agent {
    // fields
    private Point location;
    private int energy;
    private String identity;
    private Map<Direction, Double> strategy;
    private Point start;
    private Point end;

    // constructor
    public Agent(Map<Direction, Double> strategy) {
        this.location = new Point(0, 0);
        this.energy = 10;
        this.identity = "A";
        this.strategy = strategy;
        this.start = new Point(0, 0);
        this.end = new Point(0,0);
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


    public Point getStart() {
        return this.start;
    }

    public Point getEnd() {
        return this.end;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public void initstart(Display d) {
        Random random = new Random();
        int attempts = 0;
        int maxAttempts = 100; // Limit the number of attempts
        Point p = null;
    
        while (attempts < maxAttempts) {
            int x = random.nextInt(24) + 1;
            int y = random.nextInt(24) + 1;
            p = new Point(x, y);
    
            if (!occupied(d, p)) {
                break;
            }
            attempts++;
        }
    
        if (p != null && !occupied(d, p)) {
            setStart(p);
            setLocation(p);
        } else {
            System.out.println("Failed to find a valid starting position.");
        }
    }
    
    public void initend(Display d) {
        Random random = new Random();
        int attempts = 0;
        int maxAttempts = 100; // Limit the number of attempts
        Point p = null;
    
        while (attempts < maxAttempts) {
            int x = random.nextInt(24) + 1;
            int y = random.nextInt(24) + 1;
            p = new Point(x, y);
    
            if (!occupied(d, p)) {
                break;
            }
            attempts++;
        }
    
        if (p != null && !occupied(d, p)) {
            setEnd(p);
        } else {
            System.out.println("Failed to find a valid end position.");
        }
    }
    
    // Move function to move the agent in a specific direction
    public boolean move(Direction direction, Display d) {
        Point newPoint = new Point(getX(), getY());
      //  System.out.println("Direction: " + newPoint.getX() + " " + newPoint.getY());
        switch (direction) {
            case UP:
                if (getY() == 24) {
                    return false;
                }
                newPoint.setY(getY() + 1);
                break;
            case DOWN:
                if(getY() == 0){
                    return false;
                }
                newPoint.setY(getY() - 1);
                break;
            case RIGHT:
                if(getX() == 24){
                    return false;
                }
                newPoint.setX(getX() + 1);
                break;
            case LEFT:
                if(getX() == 0){
                    return false;
                }
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

    // Start moving function
    public void startmoving(Display d, Agent a) {
        int moveCount = 0;
        initend(d);
        initstart(d);
        Point currentLocation = getLocation();
        Set<Point> visited = new HashSet<>();
    
        while (!currentLocation.equals(a.getEnd())) {
            visited.add(currentLocation);
            Direction direction = decideAction();
    
            if (move(direction, d)) {
                currentLocation = getLocation();
            } else {
                while (true) {
                    direction = decideAction();
                    if (move(direction, d)) {
                        currentLocation = getLocation();
                        break;
                    }
                }
            }
            moveCount++;
        }
        System.out.println("Total number of moves : " + moveCount);
        System.out.println(a.start.getX() + " " + a.start.getY());
        System.out.println(a.end.getX() + " " + a.end.getY());
        System.out.println("Agent successfully reached the destination.");
    }
          
    

    // Getter for strategy
    public Map<Direction, Double> getStrategy() {
        return strategy;
    }

    // Setter for strategy
    public void setStrategy(Map<Direction, Double> strategy) {
        this.strategy = strategy;
    }

    // Deciding the action based on the strategy
    // Creates a random number between 0 and 1
    // Then, it goes through the strategy map and adds the probability of each action
    // If the random number is less than the cumulative probability, it returns the action
    public Direction decideAction() {

        double rand = Math.random(); // Random number between 0.0 and 1.0
        double cumulativeProbability = 0.0; // Cumulative probability that is used to choose an action

        for (Map.Entry<Direction, Double> entry : strategy.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (rand <= cumulativeProbability) {
                return entry.getKey();
            }
        }
        // In case of error; default action is UP
        return Direction.UP;
    }




    /*
    DFS to eat all food, leaving just in case


     *     public void dfs(Display d, Set<Point> visited) {
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
     */


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

    public String[] getall(Display display) {
        String[] all = new String[625];
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                Point p = new Point(i, j);
                if (display.getDisplay().containsKey(p)) {
                    all[i * 25 + j] = display.getDisplay().get(p);
                } else {
                    all[i * 25 + j] = " ";
                }
            }
        }
        return all;
    }
    // Check if a point is occupied
    public boolean occupied(Display d, Point p) {
        String[] a = getall(d);  // This gets all display elements
        Point cur = new Point(p.getX(), p.getY());
      //  System.out.println("Display is : " + a);
        System.out.println("Current Point: " + cur.getX() + ", " + cur.getY());
    
        if (d.getDisplay().containsKey(cur)) { 
            // If the point exists in the display, check what it contains
            String value = d.getDisplay().get(p);
    
            switch (value) {
                case "I":  // Hits another agent
                    return true;
                case "$":  // Hits food
                    Food f = new Food();
                    f.giveEnergy(this);
                    return false;  // The agent can occupy the same space as food
                case "0":  // Hits an obstacle
                    Obs o = new Obs();
                    o.takeEnergy(this);
                    return true;
                default:  // Empty, goal, or other case
                    return false;
            }
        } else {
            // If the point is not in the display, assume it's not occupied
            System.out.println("Point is not in the display!");
            return false;
        }
    }
    
        // Enum for directions
        public enum Direction {
            UP, DOWN, LEFT, RIGHT
        }
}

