package lab1;

public class Obs {
    private Point location;
    private String identity = "O";

    // Default constructor
    public Obs() {
        // Initialize location to default values (e.g., (0, 0))
        this.setLocation(new Point(0, 0));
    }

    // Constructor with Point parameter
    public Obs(Point c) {
        this.setLocation(c);
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void takeEnergy(Agent a) {
        a.setEnergy(a.getEnergy() - 1);
    }
}
