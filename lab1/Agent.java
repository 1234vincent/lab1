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
        int x = Math.random(24)+1; 
        int y = Math.random(24)+1;
        Point p = new Point(x,y);
        while(occupied(d, p)){
            x = Math.random(24)+1;
            y = Math.random(24)+1;
            p = new Point(x,y);
        }        
        setStart(p);
    }


    public  vodi initend(Display d) {
        int x = Math.random(24)+1;
        int y = Math.random(24)+1;
        Point p = new Point(x,y);
        while(occupied(d, p)){
            x = Math.random(24)+1;
            y = Math.random(24)+1;
            p = new Point(x,y);
        }        
        setEnd(p);
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

    //
    public void startmoving(Display d, Agent a) {
        initend(d);
        initstart(d);
        Point currentLocation = getLocation();
        Set<Point> visited = new HashSet<>();
        while(!currentLocation.equals(a.getEnd())){
            visited.add(currentLocation);
            Direction direction = decideAction();
            if(move(direction, d)){
                currentLocation = getLocation();
            }
            else{
                while(true){
                    direction = decideAction();
                    if(move(direction, d)){
                        currentLocation = getLocation();
                        break;
                    }
                }
            }
        }
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
			System.out.println("Point is not in display- what is going on!");
            return false;
        }
    }

    // Enum for directions
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
